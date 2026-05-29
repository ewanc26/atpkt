package uk.ewancroft.atpkt.generated.app.bsky.feed

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.com.atproto.repo.StrongRef

@Serializable
public data class Post(
  public val unused: String? = null,
)

@Serializable
public data class ReplyRef(
  public val root: StrongRef? = null,
  public val parent: StrongRef? = null,
)

@Serializable
public data class Entity(
  public val index: TextSlice? = null,
  public val type: String? = null,
  public val `value`: String? = null,
)

@Serializable
public data class TextSlice(
  public val start: Long? = null,
  public val end: Long? = null,
)
