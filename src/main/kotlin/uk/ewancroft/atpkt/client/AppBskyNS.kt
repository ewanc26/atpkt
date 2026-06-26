package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent
import kotlinx.serialization.json.encodeToJsonElement

import uk.ewancroft.atpkt.xrpc.Xrpc

// ── app.bsky namespace ─────────────────────────────

/**
 * Namespaced access for app.bsky.* XRPC endpoints.
 * Routes through the core Agent's request machinery.
 */
class AppBskyNS(private val agent: Agent) {
    val actor = AppBskyActorNS(agent)
    val feed = AppBskyFeedNS(agent)
    val notification = AppBskyNotificationNS(agent)
}

class AppBskyNotificationNS(private val agent: Agent) {
    suspend fun listNotifications(limit: Int = 50, cursor: String? = null): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }
}



class AppBskyActorNS(private val agent: Agent) {
    suspend fun getProfile(actor: String): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }
}

class AppBskyFeedNS(private val agent: Agent) {
    suspend fun getPostThread(uri: String): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }

    suspend fun getFeed(feed: String, cursor: String? = null, limit: Int = 50): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }

    suspend fun createPost(repoDid: String, text: String): Result<Unit> = runCatching {
        TODO("not yet implemented")
    }
}
