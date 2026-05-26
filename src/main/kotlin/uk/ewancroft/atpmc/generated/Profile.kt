package uk.ewancroft.atpmc.generated

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Profile(
  public val player: JsonElement,
  public val displayName: String,
  public val bio: String,
  public val primaryServer: JsonElement,
  public val favoriteGameMode: String,
  public val createdAt: String,
  public val updatedAt: String,
)
