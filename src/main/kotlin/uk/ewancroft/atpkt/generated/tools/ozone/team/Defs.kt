package uk.ewancroft.atpkt.generated.tools.ozone.team

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewDetailed

@Serializable
public data class Member(
  public val did: String? = null,
  public val disabled: Boolean? = null,
  public val profile: ProfileViewDetailed? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
  public val lastUpdatedBy: String? = null,
  public val role: String? = null,
)
