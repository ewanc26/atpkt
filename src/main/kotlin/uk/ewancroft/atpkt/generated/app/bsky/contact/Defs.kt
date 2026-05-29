package uk.ewancroft.atpkt.generated.app.bsky.contact

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileView

@Serializable
public data class MatchAndContactIndex(
  public val match: ProfileView? = null,
  public val contactIndex: Long? = null,
)

@Serializable
public data class SyncStatus(
  public val syncedAt: String? = null,
  public val matchesCount: Long? = null,
)

@Serializable
public data class Notification(
  public val from: String? = null,
  public val to: String? = null,
)
