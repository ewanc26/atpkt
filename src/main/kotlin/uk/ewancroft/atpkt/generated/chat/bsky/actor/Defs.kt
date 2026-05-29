package uk.ewancroft.atpkt.generated.chat.bsky.actor

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileAssociated
import uk.ewancroft.atpkt.generated.app.bsky.actor.VerificationState
import uk.ewancroft.atpkt.generated.app.bsky.actor.ViewerState
import uk.ewancroft.atpkt.generated.com.atproto.label.Label

@Serializable
public sealed interface KindUnion

@Serializable
public data class ProfileViewBasic(
  public val did: String? = null,
  public val handle: String? = null,
  public val displayName: String? = null,
  public val avatar: String? = null,
  public val associated: ProfileAssociated? = null,
  public val viewer: ViewerState? = null,
  public val labels: List<Label?>? = null,
  public val createdAt: String? = null,
  public val chatDisabled: Boolean? = null,
  public val verification: VerificationState? = null,
  public val kind: KindUnion? = null,
)

@Serializable
public data class DirectConvoMember()

@Serializable
public data class GroupConvoMember(
  public val addedBy: ProfileViewBasic? = null,
  public val role: MemberRole? = null,
)

@Serializable
public data class PastGroupConvoMember()
