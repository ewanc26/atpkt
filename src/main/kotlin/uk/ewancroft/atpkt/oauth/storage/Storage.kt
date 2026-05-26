package uk.ewancroft.atpkt.oauth.storage

import uk.ewancroft.atpkt.oauth.dpop.DpopProof

/**
 * Stores transient authorization state during the OAuth flow.
 * Mirrors atproto/packages/oauth/oauth-client/src/state-store.ts
 */
interface StateStore {
    suspend fun get(key: String): String?
    suspend fun set(key: String, value: String)
    suspend fun del(key: String)
}

/**
 * Stores long-lived authenticated sessions.
 * Mirrors atproto/packages/oauth/oauth-client/src/session-getter.ts
 */
interface SessionStore {
    suspend fun get(key: String): String?
    suspend fun set(key: String, value: String)
    suspend fun del(key: String)
}
