package uk.ewancroft.atpkt.generated.app.bsky.actor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.com.atproto.label.SelfLabels
import uk.ewancroft.atpkt.generated.com.atproto.repo.StrongRef

@Serializable
public data class Profile(
  @SerialName("\$type")
  public val type: String = "app.bsky.actor.profile",
  public val displayName: String? = null,
  public val description: String? = null,
  public val pronouns: String? = null,
  public val website: String? = null,
  public val avatar: JsonElement? = null,
  public val banner: JsonElement? = null,
  public val labels: SelfLabels? = null,
  public val joinedViaStarterPack: StrongRef? = null,
  public val pinnedPost: StrongRef? = null,
  public val createdAt: String? = null,
)
