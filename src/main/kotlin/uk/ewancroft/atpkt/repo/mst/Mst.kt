package uk.ewancroft.atpkt.repo.mst

import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.repo.cbor.CborArray
import uk.ewancroft.atpkt.repo.cbor.CborBytes
import uk.ewancroft.atpkt.repo.cbor.CborEncoder
import uk.ewancroft.atpkt.repo.cbor.CborInt
import uk.ewancroft.atpkt.repo.cbor.CborMap
import uk.ewancroft.atpkt.repo.cbor.CborNull
import uk.ewancroft.atpkt.repo.cbor.DagCborValue
import uk.ewancroft.atpkt.repo.cid.CidUtil
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest
import java.util.SortedMap
import java.util.TreeMap

// ── MST key helpers ────────────────────────────────

/**
 * Count leading zero "pairs" (2-bit groups) in the SHA-256 hash of [key].
 *
 * This is a direct port of leadingZerosOnHash from @atproto/repo:
 *   byte == 0        → 4 pairs (0000_0000)
 *   byte < 4         → 3 pairs (leading 6 bits zero)
 *   byte < 16        → 2 pairs (leading 4 bits zero)
 *   byte < 64        → 1 pair  (leading 2 bits zero)
 *   byte >= 64       → 0 pairs, stop
 *
 * The return value determines which layer of the MST the key belongs to.
 * Higher values = rarer = placed higher in the tree.
 */
fun leadingZerosOnHash(key: String): Int {
    val hash = MessageDigest.getInstance("SHA-256").digest(key.toByteArray(UTF_8))
    var total = 0
    for (byte in hash) {
        val b = byte.toInt() and 0xff
        when {
            b == 0    -> total += 4
            b < 4     -> { total += 3; break }
            b < 16    -> { total += 2; break }
            b < 64    -> { total += 1; break }
            else      -> break
        }
    }
    return total
}

/**
 * Count how many leading bytes [a] and [b] share.
 */
fun countPrefixLen(a: ByteArray, b: ByteArray): Int {
    var i = 0
    while (i < a.size && i < b.size && a[i] == b[i]) i++
    return i
}

// ── Serialisable node types ────────────────────────

/**
 * A single key-to-CID entry in the repository MST (flat, un-compressed form).
 */
@Serializable
data class MstEntry(
    val key: String,
    val cid: String
)

/**
 * A single compressed entry inside a serialised MST node.
 *
 * Mirrors the TypeScript NodeEntry shape:
 *   { p: number, k: Uint8Array, v: CID, t: CID | null }
 *
 * [prefixLen] is the number of leading bytes of the *previous* full key that
 * this entry shares with its predecessor (0 for the first entry).
 * [keySuffix] is the remaining bytes of this entry's key.
 * [cid] is the record CID this entry maps to (the "v" field).
 * [tree] is the right-hand subtree node that sits between this entry and the
 * next, or null for a leaf.
 */
@Serializable
data class MstNodeEntry(
    val prefixLen: Int,
    val keySuffix: ByteArray,
    val cid: String,
    val tree: MstNode? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MstNodeEntry) return false
        return prefixLen == other.prefixLen &&
            keySuffix.contentEquals(other.keySuffix) &&
            cid == other.cid &&
            tree == other.tree
    }

    override fun hashCode(): Int {
        var result = prefixLen
        result = 31 * result + keySuffix.contentHashCode()
        result = 31 * result + cid.hashCode()
        result = 31 * result + (tree?.hashCode() ?: 0)
        return result
    }
}

/**
 * A serialisable MST node.
 *
 * Mirrors the TypeScript NodeData shape:
 *   { l: CID | null, e: NodeEntry[] }
 *
 * [leftNode] is the left-most subtree — entries that belong at a lower layer
 * and whose keys are lexicographically before every entry in [entries].
 * This is the "l" field in the wire encoding (null → CborNull, not absent).
 *
 * [entries] are the keys stored at this node's layer, with prefix compression
 * relative to the previous key and a right-subtree pointer ("t") for entries
 * that sit between consecutive keys.
 */
@Serializable
data class MstNode(
    val leftNode: MstNode? = null,
    val entries: List<MstNodeEntry> = emptyList()
) {
    /**
     * Encode this node (and its sub-nodes) as DAG-CBOR bytes.
     * Sub-node references are included as tag-42 CID links.
     */
    fun encode(): ByteArray {
        val encoder = CborEncoder()
        return encoder.encode(toDagCborValue(encoder))
    }

    /**
     * Compute the DAG-CBOR CID for this node.
     */
    fun computeRootCid(): String = CidUtil.computeCid(encode())

    private fun toDagCborValue(encoder: CborEncoder): DagCborValue {
        // Encode the entries array; each entry has keys k, p, t, v
        // (they will be canonically sorted by CborEncoder: k < p < t < v)
        val encodedEntries = entries.map { entry ->
            CborMap(
                linkedMapOf(
                    "p" to CborInt(entry.prefixLen.toLong()),
                    "k" to CborBytes(entry.keySuffix),
                    "v" to encoder.cidLink(entry.cid),
                    "t" to (entry.tree?.let { encoder.cidLink(it.computeRootCid()) } ?: CborNull)
                )
            )
        }

        // Node map has keys "e" and "l".  After RFC 7049 canonical sort:
        //   "e" (0x61 0x65) < "l" (0x61 0x6c)
        // CborEncoder.writeMap handles the sort automatically, so insertion
        // order does not matter here.
        return CborMap(
            linkedMapOf(
                "l" to (leftNode?.let { encoder.cidLink(it.computeRootCid()) } ?: CborNull),
                "e" to CborArray(encodedEntries)
            )
        )
    }
}

// ── Diff types ─────────────────────────────────────

data class MstEntryChange(
    val key: String,
    val before: String,
    val after: String
)

data class MstDiff(
    val added: List<MstEntry> = emptyList(),
    val removed: List<MstEntry> = emptyList(),
    val changed: List<MstEntryChange> = emptyList()
)

// ── MstTree (immutable, high-level interface) ──────

/**
 * Immutable Merkle Search Tree keyed by record path (collection/rkey).
 *
 * Matches the behaviour and wire format of @atproto/repo MutableRepo / MST:
 * - Layer of each key is determined by leadingZerosOnHash(key) (2 bits per
 *   level, ~4× fanout on average).
 * - Nodes are sorted by key; sibling subtrees are encoded as tag-42 CID
 *   links pointing to their DAG-CBOR encoding.
 * - The "l" (left-most subtree) field is always present in the wire encoding
 *   (null if absent), matching the TypeScript SDK.
 *
 * Spec: https://atproto.com/specs/repository#mst
 */
class MstTree private constructor(
    private val entriesByKey: SortedMap<String, String>
) {
    constructor(entries: Collection<MstEntry> = emptyList()) : this(
        TreeMap<String, String>().apply {
            entries.forEach { entry -> put(entry.key, entry.cid) }
        }
    )

    // ── Mutation (returns new immutable instance) ──

    fun insert(key: String, cid: String): MstTree {
        val updated = TreeMap(entriesByKey)
        updated[key] = cid
        return MstTree(updated)
    }

    fun delete(key: String): MstTree {
        if (!entriesByKey.containsKey(key)) return this
        val updated = TreeMap(entriesByKey)
        updated.remove(key)
        return MstTree(updated)
    }

    // ── Query ──────────────────────────────────────

    fun get(key: String): String? = entriesByKey[key]

    fun list(): List<MstEntry> = entriesByKey.entries.map { MstEntry(it.key, it.value) }

    fun asMap(): Map<String, String> = entriesByKey.toMap()

    // ── CID / node ─────────────────────────────────

    fun rootNode(): MstNode = buildNode(list())

    fun rootCid(): String = rootNode().computeRootCid()

    // ── Diff ───────────────────────────────────────

    fun diff(other: MstTree): MstDiff {
        val added = mutableListOf<MstEntry>()
        val removed = mutableListOf<MstEntry>()
        val changed = mutableListOf<MstEntryChange>()

        val allKeys = linkedSetOf<String>().apply {
            addAll(entriesByKey.keys)
            addAll(other.entriesByKey.keys)
        }

        for (key in allKeys) {
            val left = entriesByKey[key]
            val right = other.entriesByKey[key]
            when {
                left == null && right != null -> added += MstEntry(key, right)
                left != null && right == null -> removed += MstEntry(key, left)
                left != null && right != null && left != right ->
                    changed += MstEntryChange(key, left, right)
            }
        }

        return MstDiff(added = added, removed = removed, changed = changed)
    }

    // ── Internal tree construction ─────────────────

    private fun buildNode(entries: List<MstEntry>): MstNode {
        if (entries.isEmpty()) return MstNode()
        val sorted = entries.sortedBy { it.key }
        val maxDepth = sorted.maxOf { leadingZerosOnHash(it.key) }
        return buildNodeAtDepth(sorted, maxDepth)
    }

    /**
     * Recursively build the MST node for a sorted slice of entries.
     *
     * Entries whose key hash puts them at exactly [depth] are stored in this
     * node's entry list.  Entries at lower depths are routed into sub-trees:
     * - entries before the first depth-level key → [MstNode.leftNode]
     * - entries between two consecutive depth-level keys → that key's [MstNodeEntry.tree]
     *
     * This matches the TypeScript SDK's `add` / `create` logic exactly.
     */
    private fun buildNodeAtDepth(sortedEntries: List<MstEntry>, depth: Int): MstNode {
        if (sortedEntries.isEmpty()) return MstNode()

        // Entries that live at exactly this layer
        val currentLevel = sortedEntries.filter { leadingZerosOnHash(it.key) == depth }

        // If no entries belong at this layer, recurse to the next layer down
        if (currentLevel.isEmpty()) {
            return buildNodeAtDepth(sortedEntries, depth - 1)
        }

        // ── Left subtree: entries with depth < this depth whose keys come
        //    before the first current-layer key ──
        val leftEntries = sortedEntries.filter { sub ->
            leadingZerosOnHash(sub.key) < depth && sub.key < currentLevel.first().key
        }
        val leftSubtree = if (leftEntries.isNotEmpty()) buildNodeAtDepth(leftEntries, depth - 1) else null

        // ── Build compressed entries ──────────────
        val nodeEntries = mutableListOf<MstNodeEntry>()
        var prevKey = ""

        for (i in currentLevel.indices) {
            val entry = currentLevel[i]
            val nextKey = if (i + 1 < currentLevel.size) currentLevel[i + 1].key else null

            // Right subtree: entries with depth < this depth whose keys sit
            // strictly between this key and the next current-layer key
            val rightEntries = sortedEntries.filter { sub ->
                leadingZerosOnHash(sub.key) < depth &&
                sub.key > entry.key &&
                (nextKey == null || sub.key < nextKey)
            }
            val rightSubtree = if (rightEntries.isNotEmpty()) buildNodeAtDepth(rightEntries, depth - 1) else null

            // Prefix compression
            val keyBytes = entry.key.toByteArray(UTF_8)
            val prevBytes = prevKey.toByteArray(UTF_8)
            val prefixLen = countPrefixLen(prevBytes, keyBytes)
            val suffix = keyBytes.copyOfRange(prefixLen, keyBytes.size)

            nodeEntries += MstNodeEntry(
                prefixLen = prefixLen,
                keySuffix = suffix,
                cid = entry.cid,
                tree = rightSubtree
            )

            prevKey = entry.key
        }

        return MstNode(leftNode = leftSubtree, entries = nodeEntries)
    }
}
