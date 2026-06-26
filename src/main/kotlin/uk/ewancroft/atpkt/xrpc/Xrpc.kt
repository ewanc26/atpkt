package uk.ewancroft.atpkt.xrpc

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

// ── XRPC shared utilities ──────────────────────────

/**
 * Common XRPC request and response handling.
 * Provides the shared Json configuration and error type used across the SDK.
 */
object Xrpc {
    /**
     * Shared Json instance for XRPC serialisation.
     * Lenient parsing handles the subtle differences across Lexicon implementations.
     */
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
    }

    /**
     * Standard XRPC error response envelope.
     * Returned by the server when a query or procedure fails.
     * Spec: https://atproto.com/specs/xrpc#error-responses
     */
    @Serializable
    data class XrpcError(
        val error: String,
        val message: String
    )
}
