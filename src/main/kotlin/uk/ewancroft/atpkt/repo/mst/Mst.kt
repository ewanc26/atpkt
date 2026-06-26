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

/**
 * A single key-to-CID entry in the repository MST.
 */
@Serializable
data class MstEntry(
    val key: String,
    val cid: String
)

/**
 * A compactly encoded MST tree entry.
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
 * A serialisable MST node encoded as DAG-CBOR.
 */
@Serializable
data class MstNode(
    val entries: List<MstNodeEntry> = emptyList()
) {
    fun encode(): ByteArray {
        val encoder = CborEncoder()
        return encoder.encode(toDagCborValue())
    }

    fun computeRootCid(): String = CidUtil.computeCid(encode())

    private fun toDagCborValue(): DagCborValue {
        val encodedEntries = entries.map { entry ->
            val map = linkedMapOf<String, DagCborValue>(
                "p" to CborInt(entry.prefixLen.toLong()),
                "k" to CborBytes(entry.keySuffix),
                "v" to CborEncoder().cidLink(entry.cid),
                "t" to (entry.tree?.let { CborEncoder().cidLink(it.computeRootCid()) } ?: CborNull)
            )
            CborMap(map)
        }

        return CborMap(mapOf("e" to CborArray(encodedEntries)))
    }
}

/**
 * Describes the changes between two MST instances.
 */
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

/**
 * Immutable repository tree keyed by path.
 */
class MstTree private constructor(
    private val entriesByKey: SortedMap<String, String>
) {
    constructor(entries: Collection<MstEntry> = emptyList()) : this(TreeMap<String, String>().apply {
        entries.forEach { entry -> put(entry.key, entry.cid) }
    })

    fun insert(key: String, cid: String): MstTree {
        val updated = TreeMap(entriesByKey)
        updated[key] = cid
        return MstTree(updated)
    }

    fun delete(key: String): MstTree {
        if (!entriesByKey.containsKey(key)) {
            return this
        }

        val updated = TreeMap(entriesByKey)
        updated.remove(key)
        return MstTree(updated)
    }

    fun get(key: String): String? = entriesByKey[key]

    fun list(): List<MstEntry> = entriesByKey.entries.map { MstEntry(it.key, it.value) }

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
                left != null && right != null && left != right -> changed += MstEntryChange(key, left, right)
            }
        }

        return MstDiff(added = added, removed = removed, changed = changed)
    }

    fun rootNode(): MstNode = buildNode(list())

    fun rootCid(): String = rootNode().computeRootCid()

    fun asMap(): Map<String, String> = entriesByKey.toMap()

    private fun buildNode(entries: List<MstEntry>): MstNode {
        if (entries.isEmpty()) {
            return MstNode()
        }

        val sorted = entries.sortedBy { it.key }
        val maxDepth = sorted.maxOf { keyDepth(it.key) }
        return buildNodeAtDepth(sorted, maxDepth)
    }

    private fun buildNodeAtDepth(entries: List<MstEntry>, depth: Int): MstNode {
        if (entries.isEmpty()) {
            return MstNode()
        }

        if (depth < 0) {
            return buildLeafNode(entries)
        }

        val current = entries.filter { keyDepth(it.key) == depth }
        if (current.isEmpty()) {
            return buildNodeAtDepth(entries, depth - 1)
        }

        val remaining = entries.filter { keyDepth(it.key) < depth }
        val child = if (remaining.isNotEmpty()) buildNodeAtDepth(remaining, depth - 1) else null

        val nodeEntries = current.mapIndexed { index, entry ->
            val previousKeyBytes = if (index == 0) null else current[index - 1].key.toByteArray(UTF_8)
            val keyBytes = entry.key.toByteArray(UTF_8)
            val prefixLen = previousKeyBytes?.commonPrefixLengthWith(keyBytes) ?: 0
            val suffix = keyBytes.copyOfRange(prefixLen, keyBytes.size)
            MstNodeEntry(
                prefixLen = prefixLen,
                keySuffix = suffix,
                cid = entry.cid,
                tree = if (index == current.lastIndex) child else null
            )
        }

        return MstNode(nodeEntries)
    }

    private fun buildLeafNode(entries: List<MstEntry>): MstNode {
        val sorted = entries.sortedBy { it.key }
        val nodeEntries = sorted.mapIndexed { index, entry ->
            val previousKeyBytes = if (index == 0) null else sorted[index - 1].key.toByteArray(UTF_8)
            val keyBytes = entry.key.toByteArray(UTF_8)
            val prefixLen = previousKeyBytes?.commonPrefixLengthWith(keyBytes) ?: 0
            val suffix = keyBytes.copyOfRange(prefixLen, keyBytes.size)
            MstNodeEntry(prefixLen = prefixLen, keySuffix = suffix, cid = entry.cid)
        }
        return MstNode(nodeEntries)
    }

    private fun keyDepth(key: String): Int {
        val digest = MessageDigest.getInstance("SHA-256").digest(key.toByteArray(UTF_8))
        var leadingZeroBits = 0
        for (byte in digest) {
            val unsigned = byte.toInt() and 0xff
            val zeroBits = when {
                unsigned and 0b1000_0000 != 0 -> 0
                unsigned and 0b0100_0000 != 0 -> 1
                unsigned and 0b0010_0000 != 0 -> 2
                unsigned and 0b0001_0000 != 0 -> 3
                unsigned and 0b0000_1000 != 0 -> 4
                unsigned and 0b0000_0100 != 0 -> 5
                unsigned and 0b0000_0010 != 0 -> 6
                unsigned and 0b0000_0001 != 0 -> 7
                else -> 8
            }
            leadingZeroBits += zeroBits
            if (zeroBits < 8) {
                break
            }
        }
        return leadingZeroBits / 2
    }

    private fun ByteArray.commonPrefixLengthWith(other: ByteArray): Int {
        val limit = minOf(size, other.size)
        var index = 0
        while (index < limit && this[index] == other[index]) {
            index++
        }
        return index
    }
}
