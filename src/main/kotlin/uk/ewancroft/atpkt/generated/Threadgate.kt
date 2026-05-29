package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class Threadgate(
  public val post: String,
  public val allow: List<AllowUnion>,
  public val createdAt: String,
  public val hiddenReplies: List<String>,
)

@Serializable
public sealed interface AllowUnion

@Serializable
public sealed interface AllowUnion
