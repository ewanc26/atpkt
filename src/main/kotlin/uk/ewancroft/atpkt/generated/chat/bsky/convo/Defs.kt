package uk.ewancroft.atpkt.generated.chat.bsky.convo

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class ConvoRef(
  public val unused: String? = null,
)
@Serializable
public data class MessageRef(
  public val unused: String? = null,
)
@Serializable
public sealed interface EmbedUnion
@Serializable
public data class MessageInput(
  public val unused: String? = null,
)
@Serializable
public data class MessageView(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageReferredUser(
  public val unused: String? = null,
)
@Serializable
public sealed interface DataUnion
@Serializable
public data class SystemMessageView(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataAddMember(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataRemoveMember(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataMemberJoin(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataMemberLeave(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataLockConvo(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataUnlockConvo(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataLockConvoPermanently(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataEditGroup(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataCreateJoinLink(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataEditJoinLink(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataEnableJoinLink(
  public val unused: String? = null,
)
@Serializable
public data class SystemMessageDataDisableJoinLink(
  public val unused: String? = null,
)
@Serializable
public data class DeletedMessageView(
  public val unused: String? = null,
)
@Serializable
public data class MessageViewSender(
  public val unused: String? = null,
)
@Serializable
public data class ReactionView(
  public val unused: String? = null,
)
@Serializable
public data class ReactionViewSender(
  public val unused: String? = null,
)
@Serializable
public data class MessageAndReactionView(
  public val unused: String? = null,
)
@Serializable
public sealed interface LastMessageUnion
@Serializable
public sealed interface LastReactionUnion
@Serializable
public sealed interface KindUnion
@Serializable
public data class ConvoView(
  public val unused: String? = null,
)
@Serializable
public data class DirectConvo(
  public val unused: String? = null,
)
@Serializable
public data class GroupConvo(
  public val unused: String? = null,
)
@Serializable
public data class LogBeginConvo(
  public val unused: String? = null,
)
@Serializable
public data class LogAcceptConvo(
  public val unused: String? = null,
)
@Serializable
public data class LogLeaveConvo(
  public val unused: String? = null,
)
@Serializable
public data class LogMuteConvo(
  public val unused: String? = null,
)
@Serializable
public data class LogUnmuteConvo(
  public val unused: String? = null,
)
@Serializable
public sealed interface MessageUnion
@Serializable
public data class LogCreateMessage(
  public val unused: String? = null,
)
@Serializable
public data class LogDeleteMessage(
  public val unused: String? = null,
)
@Serializable
public data class LogReadMessage(
  public val unused: String? = null,
)
@Serializable
public data class LogAddReaction(
  public val unused: String? = null,
)
@Serializable
public data class LogRemoveReaction(
  public val unused: String? = null,
)
@Serializable
public data class LogReadConvo(
  public val unused: String? = null,
)
@Serializable
public data class LogAddMember(
  public val unused: String? = null,
)
@Serializable
public data class LogRemoveMember(
  public val unused: String? = null,
)
@Serializable
public data class LogMemberJoin(
  public val unused: String? = null,
)
@Serializable
public data class LogMemberLeave(
  public val unused: String? = null,
)
@Serializable
public data class LogLockConvo(
  public val unused: String? = null,
)
@Serializable
public data class LogUnlockConvo(
  public val unused: String? = null,
)
@Serializable
public data class LogLockConvoPermanently(
  public val unused: String? = null,
)
@Serializable
public data class LogEditGroup(
  public val unused: String? = null,
)
@Serializable
public data class LogCreateJoinLink(
  public val unused: String? = null,
)
@Serializable
public data class LogEditJoinLink(
  public val unused: String? = null,
)
@Serializable
public data class LogEnableJoinLink(
  public val unused: String? = null,
)
@Serializable
public data class LogDisableJoinLink(
  public val unused: String? = null,
)
@Serializable
public data class LogIncomingJoinRequest(
  public val unused: String? = null,
)
@Serializable
public data class LogApproveJoinRequest(
  public val unused: String? = null,
)
@Serializable
public data class LogRejectJoinRequest(
  public val unused: String? = null,
)
@Serializable
public data class LogOutgoingJoinRequest(
  public val unused: String? = null,
)
