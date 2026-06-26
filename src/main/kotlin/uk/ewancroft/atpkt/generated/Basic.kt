package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Basic(
  public val unused: String? = null,
)
@Serializable
public sealed interface AccentUnion
@Serializable
public sealed interface AccentForegroundUnion
@Serializable
public sealed interface BackgroundUnion
@Serializable
public sealed interface ForegroundUnion
