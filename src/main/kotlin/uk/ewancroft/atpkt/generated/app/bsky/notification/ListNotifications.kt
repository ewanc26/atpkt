package uk.ewancroft.atpkt.generated.app.bsky.notification

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileView
import uk.ewancroft.atpkt.generated.com.atproto.label.Label

@Serializable
public data class ListNotificationsOutput(
  public val notifications: List<Notification>,
  public val cursor: String? = null,
)

@Serializable
public data class Notification(
  public val uri: String? = null,
  public val cid: String? = null,
  public val author: ProfileView? = null,
  public val reason: String? = null,
  public val reasonSubject: String? = null,
  public val record: JsonElement? = null,
  public val isRead: Boolean? = null,
  public val indexedAt: String? = null,
  public val labels: List<Label?>? = null,
)
