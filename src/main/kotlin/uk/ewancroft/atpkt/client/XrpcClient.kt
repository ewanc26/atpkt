package uk.ewancroft.atpkt.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import uk.ewancroft.atpkt.xrpc.Xrpc
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// ── XRPC client ────────────────────────────────────

/**
 * A robust, low-level XRPC client for the AT Protocol.
 * Inspired by the 'Client' interface in atproto.blue and atproto-js.
 *
 * Handles the wire protocol: serialises query/procedure calls, attaches
 * auth headers, and deserialises responses or error envelopes.
 */
@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
class XrpcClient(
    val pdsUrl: String = "https://bsky.social",
    private val httpClient: HttpClient = AtpHttpClient.client
) {
    private val json = Xrpc.json

    suspend inline fun <reified T : Any, reified R : Any> query(
        nsid: String,
        params: T? = null,
        accessJwt: String? = null
    ): R {
        val url = buildUrl(nsid, params)
        val response = httpClient.get(url) {
            accessJwt?.let { header(HttpHeaders.Authorization, "Bearer $it") }
        }
        return handleResponse(response)
    }

    suspend inline fun <reified T : Any, reified R : Any> procedure(
        nsid: String,
        body: T,
        accessJwt: String? = null
    ): R {
        val url = "$pdsUrl/xrpc/$nsid"
        val response = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            accessJwt?.let { header(HttpHeaders.Authorization, "Bearer $it") }
            setBody(json.encodeToString(body))
        }
        return handleResponse(response)
    }

    private suspend inline fun <reified R : Any> handleResponse(
        response: HttpResponse
    ): R {
        val bodyText = response.bodyAsText()
        if (!response.status.isSuccess()) {
            val error = try {
                json.decodeFromString<Xrpc.XrpcError>(bodyText)
            } catch (_: Exception) {
                Xrpc.XrpcError("UnknownError", bodyText)
            }
            throw Exception("XRPC Error: ${error.error} - ${error.message}")
        }
        return json.decodeFromString<R>(bodyText)
    }

    @OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
    private inline fun <reified T : Any> buildUrl(nsid: String, params: T?): String {
        val baseUrl = "$pdsUrl/xrpc/$nsid"
        if (params == null) return baseUrl

        val queryElement = json.parseToJsonElement(json.encodeToString(params))
        val queryPairs = queryElement.toQueryPairs()
        if (queryPairs.isEmpty()) return baseUrl

        val queryString = queryPairs.joinToString("&") { (key, value) ->
            "${encodeQueryComponent(key)}=${encodeQueryComponent(value)}"
        }
        return "$baseUrl?$queryString"
    }

    private fun JsonElement.toQueryPairs(prefix: String? = null): List<Pair<String, String>> = when (this) {
        is JsonObject -> entries.flatMap { (key, value) ->
            val nextPrefix = if (prefix.isNullOrBlank()) key else "$prefix.$key"
            value.toQueryPairs(nextPrefix)
        }
        is kotlinx.serialization.json.JsonArray -> flatMap { value -> value.toQueryPairs(prefix) }
        is JsonPrimitive -> if (prefix.isNullOrBlank()) emptyList() else listOf(prefix to content)
        JsonNull -> emptyList()
        else -> emptyList()
    }

    private fun encodeQueryComponent(value: String): String =
        URLEncoder.encode(value, StandardCharsets.UTF_8)
}
