package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewDetailed
import uk.ewancroft.atpkt.generated.app.bsky.feed.GetFeedOutput
import uk.ewancroft.atpkt.generated.app.bsky.feed.GetPostThreadOutput
import uk.ewancroft.atpkt.generated.app.bsky.notification.ListNotificationsOutput
import uk.ewancroft.atpkt.generated.com.atproto.repo.CreateRecordInput
import uk.ewancroft.atpkt.generated.com.atproto.repo.CreateRecordOutput
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
    ): Result<CreateRecordOutput> = runCatching {
        val request = CreateRecordInput(
            repo = repo,
            collection = collection,
            rkey = rkey,
            validate = true,
            record = record
        )

        val response = agent.sessionManager.client.xrpcRequest(
            method = "POST",
            endpoint = "com.atproto.repo.createRecord",
            pdsUrl = "https://bsky.social",
            body = Xrpc.json.encodeToString(CreateRecordInput.serializer(), request)
        ).getOrThrow()

        Xrpc.json.decodeFromString(CreateRecordOutput.serializer(), response)
    }

    suspend fun getProfile(actor: String): Result<ProfileViewDetailed> = runCatching {
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.actor.getProfile?actor=$actor",
            pdsUrl = "https://bsky.social"
        ).getOrThrow()

        Xrpc.json.decodeFromString(ProfileViewDetailed.serializer(), response)
    }

    suspend fun getPostThread(uri: String): Result<GetPostThreadOutput> = runCatching {
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.feed.getPostThread?uri=$uri",
            pdsUrl = "https://bsky.social"
        ).getOrThrow()

        Xrpc.json.decodeFromString(GetPostThreadOutput.serializer(), response)
    }

    suspend fun getFeed(feed: String, cursor: String? = null, limit: Int = 50): Result<GetFeedOutput> = runCatching {
        val params = buildString {
            append("feed=${java.net.URLEncoder.encode(feed, "UTF-8")}")
            append("&limit=$limit")
            cursor?.let { append("&cursor=$it") }
        }

        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.feed.getFeed?$params",
            pdsUrl = "https://bsky.social"
        ).getOrThrow()

        Xrpc.json.decodeFromString(GetFeedOutput.serializer(), response)
    }

    suspend fun listNotifications(limit: Int = 50, cursor: String? = null): Result<ListNotificationsOutput> = runCatching {
        val params = buildString {
            append("limit=$limit")
            cursor?.let { append("&cursor=$it") }
        }

        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.notification.listNotifications?$params",
            pdsUrl = "https://bsky.social"
        ).getOrThrow()

        Xrpc.json.decodeFromString(ListNotificationsOutput.serializer(), response)
    }
}
