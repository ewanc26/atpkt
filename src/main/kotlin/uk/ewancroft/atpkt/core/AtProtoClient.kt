package uk.ewancroft.atpkt.core

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import uk.ewancroft.atpkt.client.AtpHttpClient
import uk.ewancroft.atpkt.xrpc.Xrpc

// ── Low-level AT Protocol client ───────────────────

/**
 * Enhanced AT Protocol client using Ktor.
 * Handles identity resolution, authentication, and XRPC requests.
 */
class AtProtoClient(
    private val fallbackPdsUrl: String = "https://bsky.social"
) {
    private val client = AtpHttpClient.client
    
    @Serializable
    data class CreateSessionResponse(
        val did: String,
        val handle: String,
        val accessJwt: String,
        val refreshJwt: String
    )

    suspend fun xrpcRequest(
        method: String,
        endpoint: String,
        accessJwt: String? = null,
        pdsUrl: String,
        body: String? = null
    ): Result<String> = runCatching {
        val url = "$pdsUrl/xrpc/$endpoint"
        
        val response = client.request(url) {
            this.method = HttpMethod.parse(method.uppercase())
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            accessJwt?.let { header(HttpHeaders.Authorization, "Bearer $it") }
            if (body != null) {
                setBody(body)
            }
        }
        
        if (!response.status.isSuccess()) {
            val responseBody = response.bodyAsText()
            val error = try {
                val errorJson = Xrpc.json.decodeFromString<JsonObject>(responseBody)
                Xrpc.XrpcError(
                    error = errorJson["error"]?.jsonPrimitive?.content ?: "unknown",
                    message = errorJson["message"]?.jsonPrimitive?.content ?: responseBody
                )
            } catch (e: Exception) {
                Xrpc.XrpcError("unknown", responseBody)
            }
            throw Exception("Request failed: ${error.error} - ${error.message}")
        }

        response.bodyAsText()
    }
}
