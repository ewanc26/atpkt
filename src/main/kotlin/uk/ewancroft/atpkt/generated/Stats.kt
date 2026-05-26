package uk.ewancroft.atpkt.generated

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Stats(
  public val player: JsonElement,
  public val server: JsonElement,
  public val statistics: List<JsonElement>,
  public val playtimeMinutes: Long,
  public val level: Long,
  public val gamemode: String,
  public val dimension: String,
  public val syncedAt: String,
)
