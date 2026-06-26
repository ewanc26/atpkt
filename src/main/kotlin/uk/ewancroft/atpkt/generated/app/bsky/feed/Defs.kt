package uk.ewancroft.atpkt.generated.app.bsky.feed

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public sealed interface EmbedUnion
@Serializable
public data class PostView(
  public val unused: String? = null,
)
@Serializable
public data class ViewerState(
  public val unused: String? = null,
)
@Serializable
public data class ThreadContext(
  public val unused: String? = null,
)
@Serializable
public sealed interface ReasonUnion
@Serializable
public data class FeedViewPost(
  public val unused: String? = null,
)
@Serializable
public sealed interface RootUnion
@Serializable
public sealed interface ParentUnion
@Serializable
public data class DefsReplyRef(
  public val unused: String? = null,
)
@Serializable
public data class ReasonRepost(
  public val unused: String? = null,
)
@Serializable
public data class ReasonPin(
  public val unused: String? = null,
)
@Serializable
public sealed interface RepliesUnion
@Serializable
public data class ThreadViewPost(
  public val unused: String? = null,
)
@Serializable
public data class NotFoundPost(
  public val unused: String? = null,
)
@Serializable
public data class BlockedPost(
  public val unused: String? = null,
)
@Serializable
public data class BlockedAuthor(
  public val unused: String? = null,
)
@Serializable
public data class GeneratorView(
  public val unused: String? = null,
)
@Serializable
public data class GeneratorViewerState(
  public val unused: String? = null,
)
@Serializable
public data class SkeletonFeedPost(
  public val unused: String? = null,
)
@Serializable
public data class SkeletonReasonRepost(
  public val unused: String? = null,
)
@Serializable
public data class SkeletonReasonPin(
  public val unused: String? = null,
)
@Serializable
public data class ThreadgateView(
  public val unused: String? = null,
)
@Serializable
public data class Interaction(
  public val unused: String? = null,
)
