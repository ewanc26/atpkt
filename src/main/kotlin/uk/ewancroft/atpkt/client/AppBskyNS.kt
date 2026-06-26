package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent
import kotlinx.serialization.encodeToJsonElement
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewDetailed
import uk.ewancroft.atpkt.generated.app.bsky.feed.GetFeedOutput
import uk.ewancroft.atpkt.generated.app.bsky.feed.GetPostThreadOutput
import uk.ewancroft.atpkt.generated.app.bsky.feed.Post as FeedPost
import uk.ewancroft.atpkt.generated.app.bsky.notification.ListNotificationsOutput
import uk.ewancroft.atpkt.generated.com.atproto.repo.CreateRecordOutput
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
    suspend fun listNotifications(limit: Int = 50, cursor: String? = null): Result<ListNotificationsResponse> = runCatching {
        agent.com.repo.listNotifications(limit = limit, cursor = cursor).getOrThrow()
    }
}

typealias ProfileView = ProfileViewDetailed

typealias FeedViewPost = uk.ewancroft.atpkt.generated.app.bsky.feed.FeedViewPost

typealias TimelineResponse = GetFeedOutput

typealias ThreadView = GetPostThreadOutput

typealias Post = FeedPost

typealias ListNotificationsResponse = ListNotificationsOutput

typealias CreateRecordResponse = CreateRecordOutput

class AppBskyActorNS(private val agent: Agent) {
    suspend fun getProfile(actor: String): Result<ProfileView> = runCatching {
        agent.com.repo.getProfile(actor).getOrThrow()
    }
}

class AppBskyFeedNS(private val agent: Agent) {
    suspend fun getPostThread(uri: String): Result<ThreadView> = runCatching {
        agent.com.repo.getPostThread(uri).getOrThrow()
    }

    suspend fun getFeed(feed: String, cursor: String? = null, limit: Int = 50): Result<TimelineResponse> = runCatching {
        agent.com.repo.getFeed(feed, cursor, limit).getOrThrow()
    }

    suspend fun createPost(repoDid: String, text: String): Result<CreateRecordResponse> = runCatching {
        val post = Post(
            text = text,
            createdAt = java.time.Instant.now().toString()
        )
        val record = Xrpc.json.encodeToJsonElement(Post.serializer(), post)

        agent.com.repo.createRecord(
            repo = repoDid,
            collection = "app.bsky.feed.post",
            record = record
        ).getOrThrow()
    }
}
