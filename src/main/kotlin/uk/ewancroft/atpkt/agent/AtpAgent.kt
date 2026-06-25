package uk.ewancroft.atpkt.agent

import uk.ewancroft.atpkt.core.AtProtoClient
import uk.ewancroft.atpkt.oauth.OAuthSessionManager

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
    val sessions: OAuthSessionManager? = null
) {
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
