package uk.ewancroft.atpkt.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.agent.Agent
import uk.ewancroft.atpkt.xrpc.Xrpc
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant

private fun encodeQuery(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8)

private fun buildQueryString(vararg params: Pair<String, String?>): String =
    params.mapNotNull { (key, value) -> value?.let { "${encodeQuery(key)}=${encodeQuery(it)}" } }
        .joinToString("&")

private const val DEFAULT_PDS_URL = "https://bsky.social"

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
    @Serializable
    data class ChatMessage(
        @SerialName("\$type")
        val type: String = "chat.bsky.convo.defs#message",
        val text: String,
        val createdAt: String
    )

    @Serializable
    data class SendMessageRequest(
        val convoId: String,
        val message: ChatMessage
    )

    suspend fun acceptConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun addReaction(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun deleteMessageForSelf(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoAvailability(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoForMembers(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getConvoMembers(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getLog(): Result<String> = runCatching { TODO("not yet implemented") }

    suspend fun getMessages(
        convoId: String,
        limit: Int = 50,
        cursor: String? = null,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val params = buildQueryString(
            "convoId" to convoId,
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "chat.bsky.convo.getMessages?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }

    suspend fun leaveConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listConvoRequests(): Result<String> = runCatching { TODO("not yet implemented") }

    suspend fun listConvos(
        limit: Int = 50,
        cursor: String? = null,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val params = buildQueryString(
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "chat.bsky.convo.listConvos?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }

    suspend fun lockConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun muteConvo(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun removeReaction(): Result<String> = runCatching { TODO("not yet implemented") }

    suspend fun sendMessage(
        convoId: String,
        text: String,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val request = SendMessageRequest(
            convoId = convoId,
            message = ChatMessage(
                text = text,
                createdAt = Instant.now().toString()
            )
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "POST",
            endpoint = "chat.bsky.convo.sendMessage",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl,
            body = Xrpc.json.encodeToString(SendMessageRequest.serializer(), request)
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }

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
