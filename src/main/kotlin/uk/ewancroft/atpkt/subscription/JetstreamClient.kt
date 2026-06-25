package uk.ewancroft.atpkt.subscription

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

// ── Jetstream event models ─────────────────────────

/**
 * A single event from Bluesky's Jetstream firehose.
 * Jetstream is a filtered JSON-based alternative to the CBOR subscription stream.
 */
@Serializable
data class JetstreamEvent(
    val did: String,
    val time_us: Long,
    val kind: String,
    val commit: JetstreamCommit? = null
)

/**
 * Commit metadata within a Jetstream event.
 * Describes the operation (create/update/delete), collection, and record key.
 */
@Serializable
data class JetstreamCommit(
    val rev: String,
    val operation: String,
    val collection: String,
    val rkey: String,
    val record: JsonElement? = null,
    val cid: String? = null
)

// ── Jetstream client ───────────────────────────────

/**
 * Client for Bluesky's Jetstream protocol, providing a filtered JSON-based firehose.
 *
 * Supports collection-level and DID-level filtering, emitting deserialised
 * [JetstreamEvent] objects as a Kotlin coroutine Flow.
 */
class JetstreamClient(private val host: String = "jetstream1.us-east.bsky.network") {
    private val client = HttpClient(CIO) {
        install(WebSockets)
    }
    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Subscribes to Jetstream with optional filters.
     */
    fun subscribe(
        wantedCollections: List<String> = emptyList(),
        wantedDids: List<String> = emptyList()
    ): Flow<JetstreamEvent> = flow {
        val params = mutableListOf<String>()
        wantedCollections.forEach { params.add("wantedCollections=$it") }
        wantedDids.forEach { params.add("wantedDids=$it") }
        
        val query = if (params.isNotEmpty()) "?" + params.joinToString("&") else ""
        val url = "wss://$host/subscribe$query"

        client.webSocket(url) {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    try {
                        val event = json.decodeFromString<JetstreamEvent>(text)
                        emit(event)
                    } catch (e: Exception) {
                        // Log or handle malformed events
                    }
                }
            }
        }
    }
}
