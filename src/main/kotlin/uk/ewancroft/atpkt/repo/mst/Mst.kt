package uk.ewancroft.atpkt.repo.mst

import kotlinx.serialization.Serializable

import uk.ewancroft.atpkt.repo.cid.CidUtil
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Basic entry in the Merkle Search Tree (MST).
 */
@Serializable
data class MstEntry(
    val key: String,
    val cid: String
)

/**
 * A node in the Merkle Search Tree.
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
