package uk.ewancroft.atpkt.core

import uk.ewancroft.atpkt.xrpc.Xrpc
import uk.ewancroft.atpkt.lexicon.Lexicons
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

/**
 * Enhanced AT Protocol client.
 * Handles identity resolution, authentication, and XRPC requests.
 */
class AtProtoClient(
    private val fallbackPdsUrl: String = "https://bsky.social"
) {
    private val httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .followRedirects(HttpClient.Redirect.NEVER)
        .build()
    
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
        
        val requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(15))

        accessJwt?.let { requestBuilder.header("Authorization", "Bearer $it") }

        val request = when (method.uppercase()) {
            "GET" -> requestBuilder.GET().build()
            "POST" -> requestBuilder.POST(
                HttpRequest.BodyPublishers.ofString(body ?: "{}")
            ).build()
            else -> throw IllegalArgumentException("Unsupported HTTP method")
        }

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        
        if (response.statusCode() !in 200..299) {
            val error = try {
                Xrpc.json.decodeFromString<Xrpc.XrpcError>(response.body())
            } catch (e: Exception) {
                Xrpc.XrpcError("unknown", response.body())
            }
            throw Exception("Request failed: ${error.error} - ${error.message}")
        }

        response.body()
    }
}
