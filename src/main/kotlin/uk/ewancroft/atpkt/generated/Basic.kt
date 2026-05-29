package uk.ewancroft.atpkt.generated

import kotlinx.serialization.Serializable

@Serializable
public data class Basic(
  public val accent: AccentUnion,
  public val accentForeground: AccentForegroundUnion,
  public val background: BackgroundUnion,
  public val foreground: ForegroundUnion,
)

@Serializable
public sealed interface AccentUnion

@Serializable
public sealed interface AccentForegroundUnion

@Serializable
public sealed interface BackgroundUnion

@Serializable
public sealed interface ForegroundUnion

@Serializable
public sealed interface AccentUnion

@Serializable
public sealed interface AccentForegroundUnion

@Serializable
public sealed interface BackgroundUnion

@Serializable
public sealed interface ForegroundUnion
