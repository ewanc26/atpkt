package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class Post(
  public val text: String,
  public val entities: List<Entity>,
  public val facets: List<App.bsky.richtext.facet>,
  public val reply: ReplyRef,
  public val embed: EmbedUnion,
  public val langs: List<String>,
  public val labels: LabelsUnion,
  public val tags: List<String>,
  public val createdAt: String,
)

@Serializable
public sealed interface EmbedUnion

@Serializable
public sealed interface LabelsUnion

@Serializable
public sealed interface EmbedUnion

@Serializable
public sealed interface LabelsUnion
