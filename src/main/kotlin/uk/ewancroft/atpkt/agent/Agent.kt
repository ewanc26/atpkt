package uk.ewancroft.atpkt.agent

import uk.ewancroft.atpkt.core.AtProtoClient
import uk.ewancroft.atpkt.core.AtProtoSessionManager
import uk.ewancroft.atpkt.client.AppBskyNS
import uk.ewancroft.atpkt.client.ChatBskyNS
import uk.ewancroft.atpkt.client.ComAtProtoNS
import uk.ewancroft.atpkt.client.ToolsNS
import uk.ewancroft.atpkt.did.DidResolver

// ── Agent ──────────────────────────────────────────

/**
 * The central agent class for AT Protocol interactions.
 * Inspired by the official TypeScript SDK's Agent architecture.
 *
 * Provides namespaced access to com.atproto.* (via agent.com),
 * app.bsky.* (via agent.app), chat.bsky.* (via agent.chat),
 * and tools.ozone.* (via agent.tools.ozone) endpoints.
 */
class Agent(
    val sessionManager: AtProtoSessionManager,
    val didResolver: DidResolver = DidResolver()
) {
    val com = ComAtProtoNS(this)
    val app = AppBskyNS(this)
    val chat = ChatBskyNS(this)
    val tools = ToolsNS(this)

    /**
     * Resolves the PDS service endpoint for a given DID.
     */
    suspend fun resolvePds(did: String): Result<String> = runCatching {
        val doc = didResolver.resolve(did).getOrThrow()
        val pdsService = doc.service.find { it.type == "AtprotoPersonalDataServer" }
            ?: throw Exception("No PDS service found for DID: $did")
        pdsService.serviceEndpoint
    }
}
