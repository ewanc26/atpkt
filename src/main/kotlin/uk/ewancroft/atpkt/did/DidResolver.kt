package uk.ewancroft.atpkt.did

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import uk.ewancroft.atpkt.client.AtpHttpClient
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

/**
 * Resolver for DID/PLC documents with in-memory caching.
 * Uses Ktor for network requests.
 */
class DidResolver(
    private val plcUrl: String = "https://plc.directory",
    private val cacheTtlSeconds: Long = 3600
) {
    private val client = AtpHttpClient.client
    private val json = Json { ignoreUnknownKeys = true }
    private val cache = ConcurrentHashMap<String, CachedDidDocument>()

    private data class CachedDidDocument(
        val document: DidDocument,
        val expiry: Instant
    )

    @Serializable
    data class DidDocument(
        val id: String,
        val service: List<DidService> = emptyList()
    )

    @Serializable
    data class DidService(
        val id: String,
        val type: String,
        val serviceEndpoint: String
    )

    /**
     * Resolves a DID to its document, using cache if available.
     */
    suspend fun resolve(did: String): Result<DidDocument> = runCatching {
        val cached = cache[did]
        if (cached != null && cached.expiry.isAfter(Instant.now())) {
            return@runCatching cached.document
        }

        val url = when {
            did.startsWith("did:plc:") -> {
                "$plcUrl/${did.removePrefix("did:plc:")}"
            }
            did.startsWith("did:web:") -> {
                val domain = did.removePrefix("did:web:")
                "https://$domain/.well-known/did.json"
            }
            else -> throw IllegalArgumentException("Unsupported DID method: $did")
        }

        val response = client.get(url)
        
        if (!response.status.isSuccess()) {
            throw Exception("DID resolution failed: ${response.status}")
        }

        val doc = json.decodeFromString<DidDocument>(response.bodyAsText())

        // Basic validation: ensure the ID matches and services are valid
        if (doc.id != did) {
            throw Exception("DID document ID mismatch: expected $did, got ${doc.id}")
        }
        
        cache[did] = CachedDidDocument(doc, Instant.now().plusSeconds(cacheTtlSeconds))
        doc
    }
}
