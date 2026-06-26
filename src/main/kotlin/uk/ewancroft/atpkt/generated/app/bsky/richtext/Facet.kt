package uk.ewancroft.atpkt.generated.app.bsky.richtext

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public sealed interface FeaturesUnion
@Serializable
public data class Facet(
  public val unused: String? = null,
)
@Serializable
public data class Mention(
  public val unused: String? = null,
)
@Serializable
public data class Link(
  public val unused: String? = null,
)
@Serializable
public data class Tag(
  public val unused: String? = null,
)
@Serializable
public data class ByteSlice(
  public val unused: String? = null,
)
