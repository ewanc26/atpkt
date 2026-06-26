package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent
import uk.ewancroft.atpkt.generated.chat.bsky.actor.*
import uk.ewancroft.atpkt.generated.chat.bsky.convo.*
import uk.ewancroft.atpkt.generated.chat.bsky.group.*
import uk.ewancroft.atpkt.generated.chat.bsky.moderation.*

class ChatBskyNS(private val agent: Agent) {
    val actor = ChatBskyActorNS(agent)
    val convo = ChatBskyConvoNS(agent)
    val group = ChatBskyGroupNS(agent)
    val moderation = ChatBskyModerationNS(agent)
}

class ChatBskyActorNS(private val agent: Agent) {
    suspend fun declaration(params: DeclarationInput): Result<DeclarationOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.actor.declaration", params).getOrThrow()
    }
    suspend fun deleteAccount(params: DeleteAccountInput): Result<DeleteAccountOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.actor.deleteAccount", params).getOrThrow()
    }
    suspend fun exportAccountData(params: ExportAccountDataInput): Result<ExportAccountDataOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.actor.exportAccountData", params).getOrThrow()
    }
    suspend fun getStatus(params: GetStatusInput): Result<GetStatusOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.actor.getStatus", params).getOrThrow()
    }
}

class ChatBskyConvoNS(private val agent: Agent) {
    suspend fun acceptConvo(params: AcceptConvoInput): Result<AcceptConvoOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.acceptConvo", params).getOrThrow()
    }
    suspend fun addReaction(params: AddReactionInput): Result<AddReactionOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.addReaction", params).getOrThrow()
    }
    suspend fun deleteMessageForSelf(params: DeleteMessageForSelfInput): Result<DeleteMessageForSelfOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.deleteMessageForSelf", params).getOrThrow()
    }
    suspend fun getConvo(params: GetConvoInput): Result<GetConvoOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.convo.getConvo", params).getOrThrow()
    }
    suspend fun getConvoAvailability(params: GetConvoAvailabilityInput): Result<GetConvoAvailabilityOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.convo.getConvoAvailability", params).getOrThrow()
    }
    suspend fun getConvoForMembers(params: GetConvoForMembersInput): Result<GetConvoForMembersOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.convo.getConvoForMembers", params).getOrThrow()
    }
    suspend fun getConvoMembers(params: GetConvoMembersInput): Result<GetConvoMembersOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.convo.getConvoMembers", params).getOrThrow()
    }
    suspend fun getLog(params: GetLogInput): Result<GetLogOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.convo.getLog", params).getOrThrow()
    }
    suspend fun getMessages(params: GetMessagesInput): Result<GetMessagesOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.convo.getMessages", params).getOrThrow()
    }
    suspend fun leaveConvo(params: LeaveConvoInput): Result<LeaveConvoOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.leaveConvo", params).getOrThrow()
    }
    suspend fun listConvoRequests(params: ListConvoRequestsInput): Result<ListConvoRequestsOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.convo.listConvoRequests", params).getOrThrow()
    }
    suspend fun listConvos(params: ListConvosInput): Result<ListConvosOutput> = runCatching {
        agent.xrpc.executeQuery("chat.bsky.convo.listConvos", params).getOrThrow()
    }
    suspend fun lockConvo(params: LockConvoInput): Result<LockConvoOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.lockConvo", params).getOrThrow()
    }
    suspend fun muteConvo(params: MuteConvoInput): Result<MuteConvoOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.muteConvo", params).getOrThrow()
    }
    suspend fun removeReaction(params: RemoveReactionInput): Result<RemoveReactionOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.removeReaction", params).getOrThrow()
    }
    suspend fun sendMessage(params: SendMessageInput): Result<SendMessageOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.sendMessage", params).getOrThrow()
    }
    suspend fun sendMessageBatch(params: SendMessageBatchInput): Result<SendMessageBatchOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.sendMessageBatch", params).getOrThrow()
    }
    suspend fun unlockConvo(params: UnlockConvoInput): Result<UnlockConvoOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.unlockConvo", params).getOrThrow()
    }
    suspend fun unmuteConvo(params: UnmuteConvoInput): Result<UnmuteConvoOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.unmuteConvo", params).getOrThrow()
    }
    suspend fun updateAllRead(params: UpdateAllReadInput): Result<UpdateAllReadOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.updateAllRead", params).getOrThrow()
    }
    suspend fun updateRead(params: UpdateReadInput): Result<UpdateReadOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.convo.updateRead", params).getOrThrow()
    }
}

class ChatBskyGroupNS(private val agent: Agent) {
    suspend fun addMembers(params: AddMembersInput): Result<AddMembersOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.group.addMembers", params).getOrThrow()
    }
    suspend fun approveJoinRequest(params: ApproveJoinRequestInput): Result<ApproveJoinRequestOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.group.approveJoinRequest", params).getOrThrow()
    }
    suspend fun createGroup(params: CreateGroupInput): Result<CreateGroupOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.group.createGroup", params).getOrThrow()
    }
    suspend fun createJoinLink(params: CreateJoinLinkInput): Result<CreateJoinLinkOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.group.createJoinLink", params).getOrThrow()
    }
    suspend fun disableJoinLink(params: DisableJoinLinkInput): Result<DisableJoinLinkOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.group.disableJoinLink", params).getOrThrow()
    }
    suspend fun editGroup(params: EditGroupInput): Result<EditGroupOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.group.editGroup", params).getOrThrow()
    }
    suspend fun editJoinLink(params: EditJoinLinkInput): Result<EditJoinLinkOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.group.editJoinLink", params).getOrThrow()
    }
    suspend fun enableJoinLink(params: EnableJoinLinkInput): Result<EnableJoinLinkOutput> = runCatching {
        agent.xrpc.executeProcedure("chat.bsky.group.enableJoinLink", params).getOrThrow()
    }
    suspend fun getJoinLinkPreviews(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listJoinRequests(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listMutualGroups(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun rejectJoinRequest(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun removeMembers(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun requestJoin(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ChatBskyModerationNS(private val agent: Agent) {
    suspend fun getActorMetadata(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getMessageContext(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun subscribeModEvents(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun updateActorAccess(): Result<String> = runCatching { TODO("not yet implemented") }
}
