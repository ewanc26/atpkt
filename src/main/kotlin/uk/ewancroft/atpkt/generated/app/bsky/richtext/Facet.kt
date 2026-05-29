package uk.ewancroft.atpkt.generated.app.bsky.richtext

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public sealed interface FeaturesUnion

@Serializable
public data class Facet(
  public val index: ByteSlice? = null,
  public val features: List<FeaturesUnion?>? = null,
)

@Serializable
public data class Mention(
  public val did: String? = null,
)

@Serializable
public data class Link(
  public val uri: String? = null,
)

@Serializable
public data class Tag(
  public val tag: String? = null,
)

@Serializable
public data class ByteSlice(
  public val byteStart: Long? = null,
  public val byteEnd: Long? = null,
)
