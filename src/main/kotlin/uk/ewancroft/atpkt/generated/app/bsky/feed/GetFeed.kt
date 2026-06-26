package uk.ewancroft.atpkt.generated.app.bsky.feed

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class GetFeedOutput(
  public val cursor: String? = null,
  public val feed: List<FeedViewPost>,
)
