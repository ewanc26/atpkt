package uk.ewancroft.atpkt.repo.storage

// ── Blockstore interface ───────────────────────────

/**
 * Interface for a content-addressed blockstore.
 * Mirroring official ATProtocol ReadableBlockstore design.
 *
 * Blocks are keyed by CID and retrieved/stored by that identifier.
 * This is the storage primitive underlying repository operations.
 */
interface Blockstore {
    fun getBytes(cid: String): ByteArray?
    fun has(cid: String): Boolean
    fun put(cid: String, bytes: ByteArray)
}
