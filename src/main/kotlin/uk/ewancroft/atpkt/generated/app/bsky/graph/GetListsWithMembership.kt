package uk.ewancroft.atpkt.generated.app.bsky.graph

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetListsWithMembershipOutput(
  public val unused: String? = null,
)

@Serializable
public data class ListWithMembership(
  public val list: ListView? = null,
  public val listItem: ListItemView? = null,
)
