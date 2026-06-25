package uk.ewancroft.atpkt.repo.storage

import java.util.concurrent.ConcurrentHashMap

// ── In-memory blockstore ───────────────────────────

/**
 * In-memory implementation of a content-addressed blockstore.
 * Mirroring the design of the official ATProtocol MemoryBlockstore.
 *
 * All blocks live in a ConcurrentHashMap — no persistence.
 * Useful for testing and ephemeral repo operations.
 */
class InMemoryBlockstore : Blockstore {
    private val blocks = ConcurrentHashMap<String, ByteArray>()

    override fun getBytes(cid: String): ByteArray? = blocks[cid]

    override fun has(cid: String): Boolean = blocks.containsKey(cid)

    override fun put(cid: String, bytes: ByteArray) {
        blocks[cid] = bytes
    }

    fun sizeInBytes(): Long = blocks.values.sumOf { it.size.toLong() }

    fun destroy() {
        blocks.clear()
    }
}
