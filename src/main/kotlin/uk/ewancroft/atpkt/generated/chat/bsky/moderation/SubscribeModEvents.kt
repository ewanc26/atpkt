package uk.ewancroft.atpkt.generated.chat.bsky.moderation

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class EventConvoFirstMessage(
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val messageId: String? = null,
  public val recipients: List<String?>? = null,
  public val rev: String? = null,
  public val user: String? = null,
)

@Serializable
public data class EventGroupChatCreated(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val initialMemberDids: List<String?>? = null,
  public val ownerDid: String? = null,
  public val rev: String? = null,
)

@Serializable
public data class EventGroupChatMemberAdded(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val ownerDid: String? = null,
  public val requestMembersCount: Long? = null,
  public val rev: String? = null,
  public val subjectDid: String? = null,
  public val subjectFollowsOwner: Boolean? = null,
)

@Serializable
public data class EventGroupChatMemberJoined(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val joinLinkCode: String? = null,
  public val ownerDid: String? = null,
  public val rev: String? = null,
  public val subjectFollowsOwner: Boolean? = null,
)

@Serializable
public data class EventGroupChatJoinRequest(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val joinLinkCode: String? = null,
  public val ownerDid: String? = null,
  public val rev: String? = null,
  public val subjectFollowsOwner: Boolean? = null,
)

@Serializable
public data class EventGroupChatJoinRequestApproved(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val ownerDid: String? = null,
  public val rev: String? = null,
  public val subjectDid: String? = null,
)

@Serializable
public data class EventGroupChatJoinRequestRejected(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val ownerDid: String? = null,
  public val rev: String? = null,
  public val subjectDid: String? = null,
)

@Serializable
public data class EventChatAccepted(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val method: String? = null,
  public val ownerDid: String? = null,
  public val rev: String? = null,
)

@Serializable
public data class EventGroupChatMemberLeft(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val leaveMethod: String? = null,
  public val ownerDid: String? = null,
  public val rev: String? = null,
  public val subjectDid: String? = null,
)

@Serializable
public data class EventGroupChatUpdated(
  public val actorDid: String? = null,
  public val convoCreatedAt: String? = null,
  public val convoId: String? = null,
  public val createdAt: String? = null,
  public val groupMemberCount: Long? = null,
  public val groupName: String? = null,
  public val joinLinkCode: String? = null,
  public val joinLinkFollowersOnly: Boolean? = null,
  public val joinLinkRequiresApproval: Boolean? = null,
  public val lockReason: String? = null,
  public val newName: String? = null,
  public val oldName: String? = null,
  public val ownerDid: String? = null,
  public val rev: String? = null,
  public val updateType: String? = null,
)
