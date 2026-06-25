package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

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
        val params = buildString {
            append("limit=$limit")
            cursor?.let { append("&cursor=$it") }
        }
        val response = agent.com.repo.listNotificationsRequest(params).getOrThrow()
        uk.ewancroft.atpkt.xrpc.Xrpc.json.decodeFromString<ListNotificationsResponse>(response)
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
        uk.ewancroft.atpkt.xrpc.Xrpc.json.decodeFromString<ProfileView>(response)
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
        @kotlinx.serialization.SerialName("\$type")
        val type: String = "app.bsky.feed.post",
        val text: String,
        val createdAt: String
    )

    suspend fun createPost(repoDid: String, text: String): Result<String> = runCatching {
        val post = Post(text = text, createdAt = java.time.Instant.now().toString())
        val record = uk.ewancroft.atpkt.xrpc.Xrpc.json.encodeToJsonElement(Post.serializer(), post)
        
        agent.com.repo.createRecord(
            repo = repoDid,
            collection = "app.bsky.feed.post",
            record = record
        ).getOrThrow()
    }

    suspend fun getPostThread(uri: String): Result<ThreadView> = runCatching {
        val response = agent.com.repo.getPostThreadRequest(uri).getOrThrow()
        uk.ewancroft.atpkt.xrpc.Xrpc.json.decodeFromString<ThreadView>(response)
    }

    suspend fun getFeed(feed: String, cursor: String? = null, limit: Int = 50): Result<TimelineResponse> = runCatching {
        val params = buildString {
            append("feed=${java.net.URLEncoder.encode(feed, "UTF-8")}")
            append("&limit=$limit")
            cursor?.let { append("&cursor=$it") }
        }
        val response = agent.com.repo.getFeedRequest(params).getOrThrow()
        uk.ewancroft.atpkt.xrpc.Xrpc.json.decodeFromString<TimelineResponse>(response)
    }
}
