package uk.ewancroft.atpkt.generated.app.bsky.feed

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileView
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewBasic
import uk.ewancroft.atpkt.generated.app.bsky.graph.ListViewBasic
import uk.ewancroft.atpkt.generated.app.bsky.richtext.Facet
import uk.ewancroft.atpkt.generated.com.atproto.label.Label

@Serializable
public sealed interface EmbedUnion

@Serializable
public data class PostView(
  public val uri: String? = null,
  public val cid: String? = null,
  public val author: ProfileViewBasic? = null,
  public val record: JsonElement? = null,
  public val embed: EmbedUnion? = null,
  public val bookmarkCount: Long? = null,
  public val replyCount: Long? = null,
  public val repostCount: Long? = null,
  public val likeCount: Long? = null,
  public val quoteCount: Long? = null,
  public val indexedAt: String? = null,
  public val viewer: ViewerState? = null,
  public val labels: List<Label?>? = null,
  public val threadgate: ThreadgateView? = null,
  public val debug: JsonElement? = null,
)

@Serializable
public data class ViewerState(
  public val repost: String? = null,
  public val like: String? = null,
  public val bookmarked: Boolean? = null,
  public val threadMuted: Boolean? = null,
  public val replyDisabled: Boolean? = null,
  public val embeddingDisabled: Boolean? = null,
  public val pinned: Boolean? = null,
)

@Serializable
public data class ThreadContext(
  public val rootAuthorLike: String? = null,
)

@Serializable
public sealed interface ReasonUnion

@Serializable
public data class FeedViewPost(
  public val post: PostView? = null,
  public val reply: ReplyRef? = null,
  public val reason: ReasonUnion? = null,
  public val feedContext: String? = null,
  public val reqId: String? = null,
)

@Serializable
public sealed interface RootUnion

@Serializable
public sealed interface ParentUnion

@Serializable
public data class ReplyRef(
  public val root: RootUnion? = null,
  public val parent: ParentUnion? = null,
  public val grandparentAuthor: ProfileViewBasic? = null,
)

@Serializable
public data class ReasonRepost(
  public val `by`: ProfileViewBasic? = null,
  public val uri: String? = null,
  public val cid: String? = null,
  public val indexedAt: String? = null,
)

@Serializable
public data class ReasonPin()

@Serializable
public sealed interface RepliesUnion

@Serializable
public data class ThreadViewPost(
  public val post: PostView? = null,
  public val parent: ParentUnion? = null,
  public val replies: List<RepliesUnion?>? = null,
  public val threadContext: ThreadContext? = null,
)

@Serializable
public data class NotFoundPost(
  public val uri: String? = null,
  public val notFound: Boolean? = null,
)

@Serializable
public data class BlockedPost(
  public val uri: String? = null,
  public val blocked: Boolean? = null,
  public val author: BlockedAuthor? = null,
)

@Serializable
public data class BlockedAuthor(
  public val did: String? = null,
  public val viewer: uk.ewancroft.atpkt.generated.app.bsky.actor.ViewerState? = null,
)

@Serializable
public data class GeneratorView(
  public val uri: String? = null,
  public val cid: String? = null,
  public val did: String? = null,
  public val creator: ProfileView? = null,
  public val displayName: String? = null,
  public val description: String? = null,
  public val descriptionFacets: List<Facet?>? = null,
  public val avatar: String? = null,
  public val likeCount: Long? = null,
  public val acceptsInteractions: Boolean? = null,
  public val labels: List<Label?>? = null,
  public val viewer: GeneratorViewerState? = null,
  public val contentMode: String? = null,
  public val indexedAt: String? = null,
)

@Serializable
public data class GeneratorViewerState(
  public val like: String? = null,
)

@Serializable
public data class SkeletonFeedPost(
  public val post: String? = null,
  public val reason: ReasonUnion? = null,
  public val feedContext: String? = null,
)

@Serializable
public data class SkeletonReasonRepost(
  public val repost: String? = null,
)

@Serializable
public data class SkeletonReasonPin()

@Serializable
public data class ThreadgateView(
  public val uri: String? = null,
  public val cid: String? = null,
  public val record: JsonElement? = null,
  public val lists: List<ListViewBasic?>? = null,
)

@Serializable
public data class Interaction(
  public val item: String? = null,
  public val event: String? = null,
  public val feedContext: String? = null,
  public val reqId: String? = null,
)
