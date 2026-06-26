package uk.ewancroft.atpkt.repo

import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.repo.mst.MstTree

// ── Repository abstraction ─────────────────────────

/**
 * High-level repository abstraction.
 * A repository wraps an MST that stores record CIDs by path.
 */
@Serializable
data class RepoCommit(
    val did: String,
    val version: Int = 3,
    val rev: String,
    val prev: String?,
    val data: String,
    val sig: ByteArray? = null
)

class Repo(
    val did: String,
    private val mst: MstTree
) {
    fun getRootCid(): String = mst.rootCid()

    fun records(): Map<String, String> = mst.asMap()
}
