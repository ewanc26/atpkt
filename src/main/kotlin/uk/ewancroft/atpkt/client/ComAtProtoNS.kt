package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

import uk.ewancroft.atpkt.xrpc.Xrpc

// ── com.atproto namespace ──────────────────────────

/**
 * Namespaced access for com.atproto.* XRPC endpoints.
 * Provides thin wrappers around XRPC calls to the relevant PDS.
 */
class ComAtProtoNS(private val agent: Agent) {
    val repo = ComAtProtoRepoNS(agent)
}

class ComAtProtoRepoNS(private val agent: Agent) {
    suspend fun createRecord(
        repo: String,
        collection: String,
        record: JsonElement,
        rkey: String? = null
    ): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }

    suspend fun getProfile(actor: String): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }

    suspend fun getPostThread(uri: String): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }

    suspend fun getFeed(feed: String, cursor: String? = null, limit: Int = 50): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }

    suspend fun listNotifications(limit: Int = 50, cursor: String? = null): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }
}
