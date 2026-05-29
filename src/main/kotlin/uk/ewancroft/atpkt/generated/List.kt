package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class List(
  public val purpose: ListPurpose,
  public val name: String,
  public val description: String,
  public val descriptionFacets: kotlin.collections.List<App.bsky.richtext.facet>,
  public val avatar: JsonElement,
  public val labels: LabelsUnion,
  public val createdAt: String,
)

@Serializable
public sealed interface LabelsUnion

@Serializable
public sealed interface LabelsUnion
