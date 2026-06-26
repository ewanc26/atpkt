package uk.ewancroft.atpkt.agent

import uk.ewancroft.atpkt.core.AtProtoClient
import uk.ewancroft.atpkt.oauth.OAuthClient
import uk.ewancroft.atpkt.oauth.OAuthSessionManager
import uk.ewancroft.atpkt.oauth.TokenResponse
import java.security.KeyPair

// ── AtpAgent (alternate SDK entry point) ───────────

/**
 * High-level Agent inspired by atproto.blue.
 * Provides a clean entry point with namespaced access to generated APIs.
 *
 * This variant uses a flat namespace hierarchy (com.atproto, app.bsky)
 * rather than the nested Agent architecture — more explicit, less magic.
 */
class AtpAgent(
    val client: AtProtoClient = AtProtoClient(),
    val sessions: OAuthSessionManager? = null,
    val oauth: OAuthClient = OAuthClient()
) {
    suspend fun prepareAuthorization(identifier: String, clientId: String, redirectUri: String): Result<OAuthClient.AuthorizationRequest> {
        return oauth.prepareAuthorization(identifier, clientId, redirectUri)
    }

    suspend fun exchangeCode(code: String, request: OAuthClient.AuthorizationRequest): Result<TokenResponse> {
        return oauth.exchangeCode(code, request)
    }

    suspend fun refreshToken(refreshToken: String, clientId: String, tokenEndpoint: String, dpopKeyPair: KeyPair): Result<TokenResponse> {
        return oauth.refreshToken(refreshToken, clientId, tokenEndpoint, dpopKeyPair)
    }

    suspend fun logout(did: String): Result<Unit> = runCatching {
        sessions?.sessionStore?.del(did)
    }

    suspend fun isLoggedIn(did: String): Boolean {
        return sessions?.getSession(did) != null
    }
    /**
     * Namespaces are populated by generated code.
     * Example: agent.com.atproto.repo.createRecord(...)
     */
    val com = ComNamespace(this)
    val app = AppNamespace(this)

    inner class ComNamespace(val agent: AtpAgent) {
        val atproto = AtprotoNamespace(agent)
    }

    inner class AppNamespace(val agent: AtpAgent) {
        val bsky = BskyNamespace(agent)
    }

    // Further nested namespaces (AtprotoNamespace, BskyNamespace) 
    // are extended by the code generator.
}

class AtprotoNamespace(val agent: AtpAgent)
class BskyNamespace(val agent: AtpAgent)
