package uk.ewancroft.atpkt.generated

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Achievement(
  public val player: PlayerReference,
  public val server: ServerReference,
  public val achievementId: String,
  public val achievementName: String,
  public val achievementDescription: String,
  public val achievedAt: String,
  public val category: String,
  public val isChallenge: Boolean,
)
