package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class Starterpack(
  public val name: String,
  public val description: String,
  public val descriptionFacets: List<App.bsky.richtext.facet>,
  public val list: String,
  public val feeds: List<FeedItem>,
  public val createdAt: String,
)
