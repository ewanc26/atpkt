package uk.ewancroft.atpkt.generated

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Generator(
  public val did: String,
  public val displayName: String,
  public val description: String,
  public val descriptionFacets: List<App.bsky.richtext.facet>,
  public val avatar: JsonElement,
  public val acceptsInteractions: Boolean,
  public val labels: LabelsUnion,
  public val contentMode: String,
  public val createdAt: String,
)

@Serializable
public sealed interface LabelsUnion

@Serializable
public sealed interface LabelsUnion
