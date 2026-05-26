package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent

/**
 * Namespaced access for com.atproto.* XRPC endpoints.
 */
class ComAtProtoNS(private val agent: Agent) {
    val repo = ComAtProtoRepoNS(agent)
}

class ComAtProtoRepoNS(private val agent: Agent) {
    suspend fun createRecord(
        repo: String,
        collection: String,
        record: kotlinx.serialization.json.JsonElement,
        rkey: String? = null
    ): Result<String> {
        return Result.success("not-implemented")
    }

    suspend fun getProfileRequest(actor: String): Result<String> = runCatching {
        val pdsUrl = "https://bsky.social"
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.actor.getProfile?actor=$actor",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }

    suspend fun getPostThreadRequest(uri: String): Result<String> = runCatching {
        val pdsUrl = "https://bsky.social"
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.feed.getPostThread?uri=$uri",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }

    suspend fun getTimelineRequest(params: String): Result<String> = runCatching {
        val pdsUrl = "https://bsky.social"
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.feed.getTimeline?$params",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }

    suspend fun listNotificationsRequest(params: String): Result<String> = runCatching {
        val pdsUrl = "https://bsky.social"
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.notification.listNotifications?$params",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }
}
