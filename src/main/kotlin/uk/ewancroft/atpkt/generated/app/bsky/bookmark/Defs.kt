package uk.ewancroft.atpkt.generated.app.bsky.bookmark

import kotlin.String
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.com.atproto.repo.StrongRef

@Serializable
public data class Bookmark(
  public val subject: StrongRef? = null,
)

@Serializable
public sealed interface ItemUnion

@Serializable
public data class BookmarkView(
  public val subject: StrongRef? = null,
  public val createdAt: String? = null,
  public val item: ItemUnion? = null,
)
