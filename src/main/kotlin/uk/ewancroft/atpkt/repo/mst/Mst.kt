package uk.ewancroft.atpkt.repo.mst

import kotlinx.serialization.Serializable

import uk.ewancroft.atpkt.repo.cid.CidUtil
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// ── Merkle Search Tree ─────────────────────────────

/**
 * A single key-to-CID entry in the Merkle Search Tree.
 * Maps a record key (collection/rkey) to its content CID.
 */
@Serializable
data class MstEntry(
    val key: String,
    val cid: String
)

/**
 * A node in the Merkle Search Tree.
 * Each node has a fanout-based structure: entries at this level
 * plus child subtrees for deeper partitioning.
 * Spec: https://atproto.com/specs/mst
 */
@Serializable
data class MstNode(
    val cid: String,
    val entries: List<MstEntry> = emptyList(),
    val children: List<MstNode> = emptyList()
) {
    fun computeRootCid(): String {
        val data = Json.encodeToString(this).toByteArray()
        return CidUtil.computeCid(data)
    }
}
