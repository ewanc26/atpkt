package uk.ewancroft.atpkt.generated.app.bsky.bookmark

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Bookmark(
  public val unused: String? = null,
)
@Serializable
public sealed interface ItemUnion
@Serializable
public data class BookmarkView(
  public val unused: String? = null,
)
