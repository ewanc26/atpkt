package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Namespaced access for app.bsky.* XRPC endpoints.
 */
class AppBskyNS(private val agent: Agent) {
    val actor = AppBskyActorNS(agent)
    val feed = AppBskyFeedNS(agent)
}

class AppBskyActorNS(private val agent: Agent) {
    @Serializable
    data class ProfileView(
        val did: String,
        val handle: String,
        val displayName: String? = null,
        val description: String? = null,
        val avatar: String? = null,
        val indexedAt: String? = null
    )

    suspend fun getProfile(actor: String): Result<ProfileView> = runCatching {
        // We use the agent to make the request, ensuring we have a session
        val response = agent.com.repo.getProfileRequest(actor).getOrThrow()
        
        // Deserialize the profile
        uk.ewancroft.atpkt.xrpc.Xrpc.json.decodeFromString<ProfileView>(response)
    }
}

class AppBskyFeedNS(private val agent: Agent) {
    @Serializable
    data class ThreadView(
        val thread: JsonElement // Using JsonElement for flexible thread structures
    )

    suspend fun getPostThread(uri: String): Result<ThreadView> = runCatching {
        // We route this through the agent, requiring a similar request method in the repo/client namespace
        val response = agent.com.repo.getPostThreadRequest(uri).getOrThrow()
        uk.ewancroft.atpkt.xrpc.Xrpc.json.decodeFromString<ThreadView>(response)
    }
}
