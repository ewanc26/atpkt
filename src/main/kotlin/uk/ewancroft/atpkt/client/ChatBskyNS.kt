package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent

class ChatBskyNS(private val agent: Agent) {
    val actor = ChatBskyActorNS(agent)
    val convo = ChatBskyConvoNS(agent)
    val group = ChatBskyGroupNS(agent)
    val moderation = ChatBskyModerationNS(agent)
}

class ChatBskyActorNS(private val agent: Agent) {
    suspend fun declaration(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun deleteAccount(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun exportAccountData(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getStatus(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ChatBskyConvoNS(private val agent: Agent) {
    suspend fun acceptConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun addReaction(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun deleteMessageForSelf(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoAvailability(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoForMembers(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoMembers(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getLog(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getMessages(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun leaveConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listConvoRequests(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listConvos(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun lockConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun muteConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun removeReaction(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun sendMessage(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun sendMessageBatch(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun unlockConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun unmuteConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun updateAllRead(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun updateRead(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ChatBskyGroupNS(private val agent: Agent) {
    suspend fun addMembers(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun approveJoinRequest(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun createGroup(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun createJoinLink(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun disableJoinLink(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun editGroup(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun editJoinLink(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun enableJoinLink(): Result<String> = runCatching { TODO("not yet implemented") }
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
