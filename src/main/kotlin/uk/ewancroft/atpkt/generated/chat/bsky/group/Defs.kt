package uk.ewancroft.atpkt.generated.chat.bsky.group

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.chat.bsky.actor.ProfileViewBasic
import uk.ewancroft.atpkt.generated.chat.bsky.convo.ConvoView

@Serializable
public data class JoinLinkView(
  public val code: String? = null,
  public val enabledStatus: LinkEnabledStatus? = null,
  public val requireApproval: Boolean? = null,
  public val joinRule: JoinRule? = null,
  public val createdAt: String? = null,
)

@Serializable
public data class JoinLinkPreviewView(
  public val code: String? = null,
  public val name: String? = null,
  public val owner: ProfileViewBasic? = null,
  public val memberCount: Long? = null,
  public val memberLimit: Long? = null,
  public val requireApproval: Boolean? = null,
  public val joinRule: JoinRule? = null,
  public val enabledStatus: LinkEnabledStatus? = null,
  public val convo: ConvoView? = null,
  public val viewer: JoinLinkViewerState? = null,
)

@Serializable
public data class JoinLinkViewerState(
  public val requestedAt: String? = null,
)

@Serializable
public data class JoinRequestView(
  public val convoId: String? = null,
  public val requestedBy: ProfileViewBasic? = null,
  public val requestedAt: String? = null,
)

@Serializable
public data class JoinRequestConvoView(
  public val convoId: String? = null,
  public val name: String? = null,
  public val owner: ProfileViewBasic? = null,
  public val memberCount: Long? = null,
  public val memberLimit: Long? = null,
  public val requestedAt: String? = null,
)
