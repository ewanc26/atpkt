package uk.ewancroft.atpkt.oauth

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import uk.ewancroft.atpkt.client.AtpHttpClient
import java.util.concurrent.ConcurrentHashMap

// ── OAuth metadata resolution ──────────────────────

/**
 * Resolves OAuth server metadata (Authorization and Resource servers).
 * Fetches .well-known documents and caches them with issuer validation.
 * Spec: https://atproto.com/specs/oauth#discovery
 */
class MetadataResolver {
    private val client = AtpHttpClient.client
    private val json = Json { ignoreUnknownKeys = true }
    private val authCache = ConcurrentHashMap<String, AuthorizationServerMetadata>()
    private val resourceCache = ConcurrentHashMap<String, ResourceServerMetadata>()

    /**
     * Fetches the resource server metadata from a PDS.
     */
    suspend fun resolveResourceServer(pdsUrl: String): Result<ResourceServerMetadata> = runCatching {
        resourceCache[pdsUrl]?.let { return@runCatching it }

        val url = "$pdsUrl/.well-known/oauth-protected-resource"
        val response = client.get(url)
        
        if (!response.status.isSuccess()) {
            throw Exception("Failed to resolve resource server metadata: ${response.status}")
        }

        val metadata = response.body<ResourceServerMetadata>()
        resourceCache[pdsUrl] = metadata
        metadata
    }

    /**
     * Fetches the authorization server metadata.
     */
    suspend fun resolveAuthorizationServer(issuer: String): Result<AuthorizationServerMetadata> = runCatching {
        authCache[issuer]?.let { return@runCatching it }

        val url = "$issuer/.well-known/oauth-authorization-server"
        val response = client.get(url)
        
        if (!response.status.isSuccess()) {
            throw Exception("Failed to resolve authorization server metadata: ${response.status}")
        }

        val metadata = response.body<AuthorizationServerMetadata>()
        
        // Security Validations
        // 1. Validate issuer (Mix-up attack protection)
        if (metadata.issuer.trimEnd('/') != issuer.trimEnd('/')) {
            throw Exception("Invalid issuer: expected $issuer but got ${metadata.issuer}")
        }

        // 2. ATPROTO requires client_id_metadata_document
        if (!metadata.clientIdMetadataDocumentSupported) {
            throw Exception("Authorization server does not support client_id_metadata_document")
        }

        authCache[issuer] = metadata
        metadata
    }
}
