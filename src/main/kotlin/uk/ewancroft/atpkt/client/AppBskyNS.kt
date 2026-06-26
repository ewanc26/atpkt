package uk.ewancroft.atpkt.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.agent.Agent
import uk.ewancroft.atpkt.xrpc.Xrpc
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant

private fun encodeQuery(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8)

private fun buildQueryString(vararg params: Pair<String, String?>): String =
    params.mapNotNull { (key, value) -> value?.let { "${encodeQuery(key)}=${encodeQuery(it)}" } }
        .joinToString("&")

private const val DEFAULT_PDS_URL = "https://bsky.social"

// ── app.bsky namespace ─────────────────────────────

/**
 * Namespaced access for app.bsky.* XRPC endpoints.
 * Routes through the core Agent's request machinery.
 */
class AppBskyNS(private val agent: Agent) {
    val actor = AppBskyActorNS(agent)
    val feed = AppBskyFeedNS(agent)
    val graph = AppBskyGraphNS(agent)
    val notification = AppBskyNotificationNS(agent)
}

class AppBskyNotificationNS(private val agent: Agent) {
    @Serializable
    data class Notification(
        val uri: String,
        val cid: String,
        val author: JsonElement,
        val reason: String,
        val record: JsonElement,
        val isRead: Boolean,
        val indexedAt: String
    )

    @Serializable
    data class ListNotificationsResponse(
        val notifications: List<Notification>,
        val cursor: String? = null
    )

    suspend fun listNotifications(limit: Int = 50, cursor: String? = null): Result<ListNotificationsResponse> = runCatching {
        val params = buildQueryString(
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.com.repo.listNotificationsRequest(params).getOrThrow()
        Xrpc.json.decodeFromString<ListNotificationsResponse>(response)
    }
}

class AppBskyActorNS(private val agent: Agent) {
    @Serializable
    data class ProfileView(
        val did: String,
        val handle: String,
        val displayName: String? = null,
        val description: String? = null,
        val avatar: String? = null,
        val banner: String? = null,
        val followersCount: Long? = null,
        val followsCount: Long? = null,
        val postsCount: Long? = null,
        val indexedAt: String? = null
    )

    suspend fun getProfile(actor: String): Result<ProfileView> = runCatching {
        val response = agent.com.repo.getProfileRequest(actor).getOrThrow()
        Xrpc.json.decodeFromString<ProfileView>(response)
    }

    suspend fun searchActors(
        term: String,
        limit: Int = 25,
        cursor: String? = null,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val params = buildQueryString(
            "term" to term,
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.actor.searchActors?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }

    suspend fun getSuggestions(
        limit: Int = 50,
        cursor: String? = null,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val params = buildQueryString(
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.actor.getSuggestions?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }
}

class AppBskyFeedNS(private val agent: Agent) {
    @Serializable
    data class ThreadView(
        val thread: JsonElement
    )

    @Serializable
    data class FeedViewPost(
        val post: JsonElement,
        val reply: JsonElement? = null,
        val reason: JsonElement? = null
    )

    @Serializable
    data class TimelineResponse(
        val feed: List<FeedViewPost>,
        val cursor: String? = null
    )

    @Serializable
    data class Post(
        @SerialName("\$type")
        val type: String = "app.bsky.feed.post",
        val text: String,
        val createdAt: String
    )

    suspend fun createPost(
        repoDid: String,
        text: String,
        accessJwt: String? = null
    ): Result<String> = runCatching {
        val post = Post(text = text, createdAt = Instant.now().toString())
        val record = Xrpc.json.encodeToJsonElement(Post.serializer(), post)

        agent.com.repo.createRecord(
            repo = repoDid,
            collection = "app.bsky.feed.post",
            record = record,
            accessJwt = accessJwt
        ).getOrThrow()
    }

    suspend fun getPostThread(uri: String): Result<ThreadView> = runCatching {
        val response = agent.com.repo.getPostThreadRequest(uri).getOrThrow()
        Xrpc.json.decodeFromString<ThreadView>(response)
    }

    suspend fun getTimeline(
        cursor: String? = null,
        limit: Int = 50,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<TimelineResponse> = runCatching {
        val params = buildQueryString(
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.feed.getTimeline?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<TimelineResponse>(response)
    }

    suspend fun getFeed(feed: String, cursor: String? = null, limit: Int = 50): Result<TimelineResponse> = runCatching {
        val params = buildQueryString(
            "feed" to feed,
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.com.repo.getFeedRequest(params).getOrThrow()
        Xrpc.json.decodeFromString<TimelineResponse>(response)
    }
}

class AppBskyGraphNS(private val agent: Agent) {
    @Serializable
    data class FollowRecord(
        @SerialName("\$type")
        val type: String = "app.bsky.graph.follow",
        val subject: String,
        val createdAt: String
    )

    suspend fun getFollows(
        actor: String,
        limit: Int = 50,
        cursor: String? = null,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val params = buildQueryString(
            "actor" to actor,
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.graph.getFollows?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }

    suspend fun getFollowers(
        actor: String,
        limit: Int = 50,
        cursor: String? = null,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val params = buildQueryString(
            "actor" to actor,
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.graph.getFollowers?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }

    suspend fun follow(
        repoDid: String,
        subjectDid: String,
        accessJwt: String? = null
    ): Result<String> = runCatching {
        val record = FollowRecord(
            subject = subjectDid,
            createdAt = Instant.now().toString()
        )
        val jsonRecord = Xrpc.json.encodeToJsonElement(FollowRecord.serializer(), record)

        agent.com.repo.createRecord(
            repo = repoDid,
            collection = "app.bsky.graph.follow",
            record = jsonRecord,
            accessJwt = accessJwt
        ).getOrThrow()
    }

    suspend fun unfollow(
        repoDid: String,
        rkey: String,
        accessJwt: String? = null
    ): Result<String> = runCatching {
        agent.com.repo.deleteRecord(
            repo = repoDid,
            collection = "app.bsky.graph.follow",
            rkey = rkey,
            accessJwt = accessJwt
        ).getOrThrow()
    }
}
