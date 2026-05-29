package uk.ewancroft.atpkt.generated.app.bsky.feed

import kotlin.String
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileView

@Serializable
public data class GetLikesOutput(
  public val unused: String? = null,
)

@Serializable
public data class Like(
  public val indexedAt: String? = null,
  public val createdAt: String? = null,
  public val actor: ProfileView? = null,
)
