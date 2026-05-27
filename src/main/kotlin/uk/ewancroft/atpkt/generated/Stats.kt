package uk.ewancroft.atpkt.generated

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class Stats(
  public val player: PlayerReference,
  public val server: ServerReference,
  public val statistics: List<Statistic>,
  public val playtimeMinutes: Long,
  public val level: Long,
  public val gamemode: String,
  public val dimension: String,
  public val syncedAt: String,
)
