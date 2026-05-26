package uk.ewancroft.atpmc.xrpc

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

/**
 * Common XRPC request and response handling.
 */
object Xrpc {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
    }

    /**
     * Standard XRPC error response.
     */
    data class XrpcError(
        val error: String,
        val message: String
    )
}
