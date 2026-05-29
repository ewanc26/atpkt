package uk.ewancroft.atpkt.generated.app.bsky.embed

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewBasic
import uk.ewancroft.atpkt.generated.app.bsky.feed.BlockedAuthor
import uk.ewancroft.atpkt.generated.com.atproto.label.Label
import uk.ewancroft.atpkt.generated.com.atproto.repo.StrongRef

@Serializable
public data class Record(
  public val record: StrongRef? = null,
)

@Serializable
public sealed interface RecordUnion

@Serializable
public data class View(
  public val record: RecordUnion? = null,
)

@Serializable
public sealed interface EmbedsUnion

@Serializable
public data class ViewRecord(
  public val uri: String? = null,
  public val cid: String? = null,
  public val author: ProfileViewBasic? = null,
  public val `value`: JsonElement? = null,
  public val labels: List<Label?>? = null,
  public val replyCount: Long? = null,
  public val repostCount: Long? = null,
  public val likeCount: Long? = null,
  public val quoteCount: Long? = null,
  public val embeds: List<EmbedsUnion?>? = null,
  public val indexedAt: String? = null,
)

@Serializable
public data class ViewNotFound(
  public val uri: String? = null,
  public val notFound: Boolean? = null,
)

@Serializable
public data class ViewBlocked(
  public val uri: String? = null,
  public val blocked: Boolean? = null,
  public val author: BlockedAuthor? = null,
)

@Serializable
public data class ViewDetached(
  public val uri: String? = null,
  public val detached: Boolean? = null,
)
