package uk.ewancroft.atpkt.generated

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Achievement(
  public val player: JsonElement,
  public val server: JsonElement,
  public val achievementId: String,
  public val achievementName: String,
  public val achievementDescription: String,
  public val achievedAt: String,
  public val category: String,
  public val isChallenge: Boolean,
)
