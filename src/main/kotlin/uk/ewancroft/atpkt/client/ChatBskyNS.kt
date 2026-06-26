package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent

class ChatBskyNS(private val agent: Agent) {
    val actor = ChatBskyActorNS(agent)
    val convo = ChatBskyConvoNS(agent)
    val group = ChatBskyGroupNS(agent)
    val moderation = ChatBskyModerationNS(agent)
}

class ChatBskyActorNS(private val agent: Agent) {
    suspend fun declaration(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun deleteAccount(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun exportAccountData(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun getStatus(): Result<Unit> = runCatching { TODO("not yet implemented") }
}

class ChatBskyConvoNS(private val agent: Agent) {
    suspend fun acceptConvo(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun addReaction(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun deleteMessageForSelf(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun getConvo(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoAvailability(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoForMembers(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoMembers(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun getLog(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun getMessages(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun leaveConvo(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun listConvoRequests(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun listConvos(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun lockConvo(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun muteConvo(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun removeReaction(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun sendMessage(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun sendMessageBatch(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun unlockConvo(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun unmuteConvo(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun updateAllRead(): Result<Unit> = runCatching { TODO("not yet implemented") }
    suspend fun updateRead(): Result<Unit> = runCatching { TODO("not yet implemented") }
}

class ChatBskyGroupNS(private val agent: Agent) {
    suspend fun addMembers(): Result<Unit> = runCatching { TODO("not yet implemented") }
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
