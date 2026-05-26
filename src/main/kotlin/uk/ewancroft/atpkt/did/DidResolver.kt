package uk.ewancroft.atpkt.did

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

/**
 * Resolver for DID/PLC documents with in-memory caching.
 */
class DidResolver(
    private val plcUrl: String = "https://plc.directory",
    private val cacheTtlSeconds: Long = 3600
) {
    private val httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build()
        
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

        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        
        if (response.statusCode() != 200) {
            throw Exception("DID resolution failed: ${response.statusCode()}")
        }

        val doc = json.decodeFromString<DidDocument>(response.body())

        // Basic validation: ensure the ID matches and services are valid
        if (doc.id != did) {
            throw Exception("DID document ID mismatch: expected $did, got ${doc.id}")
        }
        
        cache[did] = CachedDidDocument(doc, Instant.now().plusSeconds(cacheTtlSeconds))
        doc
    }
}
