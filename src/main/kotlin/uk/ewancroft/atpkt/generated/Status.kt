package uk.ewancroft.atpkt.generated

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Status(
  public val status: String,
  public val embed: EmbedUnion,
  public val durationMinutes: Long,
  public val createdAt: String,
)

@Serializable
public sealed interface EmbedUnion

@Serializable
public sealed interface EmbedUnion
