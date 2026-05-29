package uk.ewancroft.atpkt.generated.chat.bsky.convo

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.app.bsky.richtext.Facet
import uk.ewancroft.atpkt.generated.chat.bsky.actor.MemberRole
import uk.ewancroft.atpkt.generated.chat.bsky.actor.ProfileViewBasic
import uk.ewancroft.atpkt.generated.chat.bsky.group.JoinLinkView

@Serializable
public data class ConvoRef(
  public val did: String? = null,
  public val convoId: String? = null,
)

@Serializable
public data class MessageRef(
  public val did: String? = null,
  public val convoId: String? = null,
  public val messageId: String? = null,
)

@Serializable
public sealed interface EmbedUnion

@Serializable
public data class MessageInput(
  public val text: String? = null,
  public val facets: List<Facet?>? = null,
  public val embed: EmbedUnion? = null,
)

@Serializable
public data class MessageView(
  public val id: String? = null,
  public val rev: String? = null,
  public val text: String? = null,
  public val facets: List<Facet?>? = null,
  public val embed: EmbedUnion? = null,
  public val reactions: List<ReactionView?>? = null,
  public val sender: MessageViewSender? = null,
  public val sentAt: String? = null,
)

@Serializable
public data class SystemMessageReferredUser(
  public val did: String? = null,
)

@Serializable
public sealed interface DataUnion

@Serializable
public data class SystemMessageView(
  public val id: String? = null,
  public val rev: String? = null,
  public val sentAt: String? = null,
  public val `data`: DataUnion? = null,
)

@Serializable
public data class SystemMessageDataAddMember(
  public val member: SystemMessageReferredUser? = null,
  public val role: MemberRole? = null,
  public val addedBy: SystemMessageReferredUser? = null,
)

@Serializable
public data class SystemMessageDataRemoveMember(
  public val member: SystemMessageReferredUser? = null,
  public val removedBy: SystemMessageReferredUser? = null,
)

@Serializable
public data class SystemMessageDataMemberJoin(
  public val member: SystemMessageReferredUser? = null,
  public val role: MemberRole? = null,
  public val approvedBy: SystemMessageReferredUser? = null,
)

@Serializable
public data class SystemMessageDataMemberLeave(
  public val member: SystemMessageReferredUser? = null,
)

@Serializable
public data class SystemMessageDataLockConvo(
  public val lockedBy: SystemMessageReferredUser? = null,
)

@Serializable
public data class SystemMessageDataUnlockConvo(
  public val unlockedBy: SystemMessageReferredUser? = null,
)

@Serializable
public data class SystemMessageDataLockConvoPermanently(
  public val lockedBy: SystemMessageReferredUser? = null,
)

@Serializable
public data class SystemMessageDataEditGroup(
  public val oldName: String? = null,
  public val newName: String? = null,
)

@Serializable
public data class SystemMessageDataCreateJoinLink()

@Serializable
public data class SystemMessageDataEditJoinLink()

@Serializable
public data class SystemMessageDataEnableJoinLink()

@Serializable
public data class SystemMessageDataDisableJoinLink()

@Serializable
public data class DeletedMessageView(
  public val id: String? = null,
  public val rev: String? = null,
  public val sender: MessageViewSender? = null,
  public val sentAt: String? = null,
)

@Serializable
public data class MessageViewSender(
  public val did: String? = null,
)

@Serializable
public data class ReactionView(
  public val `value`: String? = null,
  public val sender: ReactionViewSender? = null,
  public val createdAt: String? = null,
)

@Serializable
public data class ReactionViewSender(
  public val did: String? = null,
)

@Serializable
public data class MessageAndReactionView(
  public val message: MessageView? = null,
  public val reaction: ReactionView? = null,
)

@Serializable
public sealed interface LastMessageUnion

@Serializable
public sealed interface LastReactionUnion

@Serializable
public sealed interface KindUnion

@Serializable
public data class ConvoView(
  public val id: String? = null,
  public val rev: String? = null,
  public val members: List<ProfileViewBasic?>? = null,
  public val lastMessage: LastMessageUnion? = null,
  public val lastReaction: LastReactionUnion? = null,
  public val muted: Boolean? = null,
  public val status: ConvoStatus? = null,
  public val unreadCount: Long? = null,
  public val kind: KindUnion? = null,
)

@Serializable
public data class DirectConvo()

@Serializable
public data class GroupConvo(
  public val name: String? = null,
  public val memberCount: Long? = null,
  public val createdAt: String? = null,
  public val joinRequestCount: Long? = null,
  public val joinLink: JoinLinkView? = null,
  public val memberLimit: Long? = null,
  public val lockStatus: ConvoLockStatus? = null,
)

@Serializable
public data class LogBeginConvo(
  public val rev: String? = null,
  public val convoId: String? = null,
)

@Serializable
public data class LogAcceptConvo(
  public val rev: String? = null,
  public val convoId: String? = null,
)

@Serializable
public data class LogLeaveConvo(
  public val rev: String? = null,
  public val convoId: String? = null,
)

@Serializable
public data class LogMuteConvo(
  public val rev: String? = null,
  public val convoId: String? = null,
)

@Serializable
public data class LogUnmuteConvo(
  public val rev: String? = null,
  public val convoId: String? = null,
)

@Serializable
public sealed interface MessageUnion

@Serializable
public data class LogCreateMessage(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: MessageUnion? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogDeleteMessage(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: MessageUnion? = null,
)

@Serializable
public data class LogReadMessage(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: MessageUnion? = null,
)

@Serializable
public data class LogAddReaction(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: MessageUnion? = null,
  public val reaction: ReactionView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogRemoveReaction(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: MessageUnion? = null,
  public val reaction: ReactionView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogReadConvo(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: MessageUnion? = null,
)

@Serializable
public data class LogAddMember(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogRemoveMember(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogMemberJoin(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogMemberLeave(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogLockConvo(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogUnlockConvo(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogLockConvoPermanently(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
  public val relatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class LogEditGroup(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
)

@Serializable
public data class LogCreateJoinLink(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
)

@Serializable
public data class LogEditJoinLink(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
)

@Serializable
public data class LogEnableJoinLink(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
)

@Serializable
public data class LogDisableJoinLink(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val message: SystemMessageView? = null,
)

@Serializable
public data class LogIncomingJoinRequest(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val member: ProfileViewBasic? = null,
)

@Serializable
public data class LogApproveJoinRequest(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val member: ProfileViewBasic? = null,
)

@Serializable
public data class LogRejectJoinRequest(
  public val rev: String? = null,
  public val convoId: String? = null,
  public val member: ProfileViewBasic? = null,
)

@Serializable
public data class LogOutgoingJoinRequest(
  public val rev: String? = null,
  public val convoId: String? = null,
)
