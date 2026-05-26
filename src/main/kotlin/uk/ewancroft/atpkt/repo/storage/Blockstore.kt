package uk.ewancroft.atpkt.repo.storage

/**
 * Interface for a content-addressed blockstore.
 * Mirroring official ATProtocol ReadableBlockstore design.
 */
interface Blockstore {
    fun getBytes(cid: String): ByteArray?
    fun has(cid: String): Boolean
    fun put(cid: String, bytes: ByteArray)
}
