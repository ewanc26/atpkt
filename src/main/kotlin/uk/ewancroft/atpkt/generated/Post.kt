package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Post(
  public val unused: String? = null,
)
@Serializable
public sealed interface PostEmbedUnion
@Serializable
public sealed interface PostLabelsUnion
