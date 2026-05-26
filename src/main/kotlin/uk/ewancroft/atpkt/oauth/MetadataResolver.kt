package uk.ewancroft.atpkt.oauth

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

/**
 * Resolves OAuth authorization server metadata.
 * Mirrors the logic found in atproto/packages/oauth/oauth-client/src/oauth-authorization-server-metadata-resolver.ts
 */
class MetadataResolver(
    private val httpClient: HttpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build()
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val cache = ConcurrentHashMap<String, AuthorizationServerMetadata>()

    @Serializable
    data class AuthorizationServerMetadata(
        val issuer: String,
        val authorization_endpoint: String,
        val token_endpoint: String,
        val jwks_uri: String,
        val response_types_supported: List<String>,
        val grant_types_supported: List<String>,
        val dpop_signing_alg_values_supported: List<String>
    )

    suspend fun resolve(pdsUrl: String): Result<AuthorizationServerMetadata> = runCatching {
        cache[pdsUrl]?.let { return@runCatching it }

        val uri = URI.create("$pdsUrl/.well-known/oauth-authorization-server")
        val request = HttpRequest.newBuilder().uri(uri).GET().build()
        
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        
        if (response.statusCode() != 200) {
            throw Exception("Failed to resolve OAuth metadata: ${response.statusCode()}")
        }

        val metadata = json.decodeFromString<AuthorizationServerMetadata>(response.body())
        cache[pdsUrl] = metadata
        metadata
    }
}
