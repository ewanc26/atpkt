package uk.ewancroft.atpkt.oauth.storage

import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

/**
 * Thread-safe in-memory state store with time-based expiry.
 */
class InMemoryStateStore(
    private val ttl: Duration = 10.minutes
) : StateStore {
    private data class Entry(
        val value: String,
        val expiresAtMillis: Long
    )

    private val states = ConcurrentHashMap<String, Entry>()

    override suspend fun get(key: String): String? {
        val entry = states[key] ?: return null
        if (entry.isExpired()) {
            states.remove(key, entry)
            return null
        }

        return entry.value
    }

    override suspend fun set(key: String, value: String) {
        states[key] = Entry(
            value = value,
            expiresAtMillis = System.currentTimeMillis() + ttl.inWholeMilliseconds
        )
    }

    override suspend fun del(key: String) {
        states.remove(key)
    }

    private fun Entry.isExpired(): Boolean = expiresAtMillis <= System.currentTimeMillis()
}
