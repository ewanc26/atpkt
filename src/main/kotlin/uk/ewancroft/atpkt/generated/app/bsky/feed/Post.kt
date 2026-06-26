package uk.ewancroft.atpkt.generated.app.bsky.feed

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.app.bsky.richtext.Facet
import uk.ewancroft.atpkt.generated.com.atproto.label.SelfLabels
import uk.ewancroft.atpkt.generated.com.atproto.repo.StrongRef

@Serializable
public data class Post(
  @SerialName("\$type")
  public val type: String = "app.bsky.feed.post",
  public val text: String,
  public val entities: List<Entity>? = null,
  public val facets: List<Facet?>? = null,
  public val reply: ReplyRef? = null,
  public val embed: JsonElement? = null,
  public val langs: List<String>? = null,
  public val labels: SelfLabels? = null,
  public val tags: List<String>? = null,
  public val createdAt: String,
)

@Serializable
public data class ReplyRef(
  public val root: StrongRef,
  public val parent: StrongRef,
)

@Serializable
public data class Entity(
  public val index: TextSlice,
  public val type: String,
  public val value: String,
)

@Serializable
public data class TextSlice(
  public val start: Long,
  public val end: Long,
)
