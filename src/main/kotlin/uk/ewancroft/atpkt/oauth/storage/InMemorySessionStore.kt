package uk.ewancroft.atpkt.oauth.storage

import java.util.concurrent.ConcurrentHashMap

/**
 * Thread-safe in-memory session store for ephemeral sessions and tests.
 */
class InMemorySessionStore : ListableSessionStore {
    private val sessions = ConcurrentHashMap<String, String>()

    override suspend fun get(key: String): String? = sessions[key]

    override suspend fun set(key: String, value: String) {
        sessions[key] = value
    }

    override suspend fun del(key: String) {
        sessions.remove(key)
    }

    override suspend fun list(): List<String> = sessions.keys.sorted()
}
