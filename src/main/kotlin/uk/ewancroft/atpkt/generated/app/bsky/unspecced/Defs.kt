package uk.ewancroft.atpkt.generated.app.bsky.unspecced

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewBasic
import uk.ewancroft.atpkt.generated.app.bsky.feed.BlockedAuthor
import uk.ewancroft.atpkt.generated.app.bsky.feed.PostView

@Serializable
public data class SkeletonSearchPost(
  public val uri: String? = null,
)

@Serializable
public data class SkeletonSearchActor(
  public val did: String? = null,
)

@Serializable
public data class SkeletonSearchStarterPack(
  public val uri: String? = null,
)

@Serializable
public data class TrendingTopic(
  public val topic: String? = null,
  public val displayName: String? = null,
  public val description: String? = null,
  public val link: String? = null,
)

@Serializable
public data class SkeletonTrend(
  public val topic: String? = null,
  public val displayName: String? = null,
  public val link: String? = null,
  public val startedAt: String? = null,
  public val postCount: Long? = null,
  public val status: String? = null,
  public val category: String? = null,
  public val dids: List<String?>? = null,
)

@Serializable
public data class TrendView(
  public val topic: String? = null,
  public val displayName: String? = null,
  public val link: String? = null,
  public val startedAt: String? = null,
  public val postCount: Long? = null,
  public val status: String? = null,
  public val category: String? = null,
  public val actors: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class ThreadItemPost(
  public val post: PostView? = null,
  public val moreParents: Boolean? = null,
  public val moreReplies: Long? = null,
  public val opThread: Boolean? = null,
  public val hiddenByThreadgate: Boolean? = null,
  public val mutedByViewer: Boolean? = null,
)

@Serializable
public data class ThreadItemNoUnauthenticated()

@Serializable
public data class ThreadItemNotFound()

@Serializable
public data class ThreadItemBlocked(
  public val author: BlockedAuthor? = null,
)

@Serializable
public data class AgeAssuranceState(
  public val lastInitiatedAt: String? = null,
  public val status: String? = null,
)

@Serializable
public data class AgeAssuranceEvent(
  public val createdAt: String? = null,
  public val status: String? = null,
  public val attemptId: String? = null,
  public val email: String? = null,
  public val initIp: String? = null,
  public val initUa: String? = null,
  public val completeIp: String? = null,
  public val completeUa: String? = null,
)
