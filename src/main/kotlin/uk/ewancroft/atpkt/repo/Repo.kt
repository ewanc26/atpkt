package uk.ewancroft.atpkt.repo

import uk.ewancroft.atpkt.repo.mst.MstNode
import kotlinx.serialization.Serializable

// ── Repository abstraction ─────────────────────────

/**
 * High-level repository abstraction.
 * Mirroring official ATProtocol 'Repo' class structure.
 *
 * A Repo wraps a Merkle Search Tree (MST) that stores the user's
 * collection records as CID-addressed blocks.
 */
@Serializable
data class RepoCommit(
    val did: String,
    val version: Int = 3,
    val rev: String,
    val prev: String?,
    val data: String // Root CID of the MST
)

class Repo(
    val did: String,
    private val mst: MstNode
) {
    fun getRootCid(): String = mst.computeRootCid()
    
    // Future: commit operations, MST diffing, and block-map management.
}
