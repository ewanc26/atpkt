package uk.ewancroft.atpkt.generated.app.bsky.graph

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetStarterPacksWithMembershipOutput(
  public val unused: String? = null,
)

@Serializable
public data class StarterPackWithMembership(
  public val starterPack: StarterPackView? = null,
  public val listItem: ListItemView? = null,
)
