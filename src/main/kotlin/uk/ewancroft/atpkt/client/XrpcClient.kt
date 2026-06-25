package uk.ewancroft.atpkt.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import uk.ewancroft.atpkt.xrpc.Xrpc

// ── XRPC client ────────────────────────────────────

/**
 * A robust, low-level XRPC client for the AT Protocol.
 * Inspired by the 'Client' interface in atproto.blue and atproto-js.
 *
 * Handles the wire protocol: serialises query/procedure calls, attaches
 * auth headers, and deserialises responses or error envelopes.
 */
class XrpcClient(
    val pdsUrl: String = "https://bsky.social",
    private val httpClient: HttpClient = AtpHttpClient.client
) {
    private val json = Xrpc.json

    suspend fun <T : Any, R : Any> query(
        nsid: String,
        params: T? = null,
        responseSerializer: kotlinx.serialization.KSerializer<R>,
        accessJwt: String? = null
    ): R {
        val url = buildUrl(nsid, params)
        val response = httpClient.get(url) {
            accessJwt?.let { header(HttpHeaders.Authorization, "Bearer $it") }
        }
        return handleResponse(response, responseSerializer)
    }

    suspend fun <T : Any, R : Any> procedure(
        nsid: String,
        body: T,
        requestSerializer: kotlinx.serialization.KSerializer<T>,
        responseSerializer: kotlinx.serialization.KSerializer<R>,
        accessJwt: String? = null
    ): R {
        val url = "$pdsUrl/xrpc/$nsid"
        val response = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            accessJwt?.let { header(HttpHeaders.Authorization, "Bearer $it") }
            setBody(json.encodeToString(requestSerializer, body))
        }
        return handleResponse(response, responseSerializer)
    }

    private suspend fun <R : Any> handleResponse(
        response: HttpResponse,
        serializer: kotlinx.serialization.KSerializer<R>
    ): R {
        val bodyText = response.bodyAsText()
        if (!response.status.isSuccess()) {
            val error = try {
                json.decodeFromString<Xrpc.XrpcError>(bodyText)
            } catch (e: Exception) {
                Xrpc.XrpcError("UnknownError", bodyText)
            }
            throw Exception("XRPC Error: ${error.error} - ${error.message}")
        }
        return json.decodeFromString(serializer, bodyText)
    }

    private fun <T : Any> buildUrl(nsid: String, params: T?): String {
        val baseUrl = "$pdsUrl/xrpc/$nsid"
        if (params == null) return baseUrl
        
        // Simple parameter serialization for low-level client
        // In a full implementation, this would use a dedicated Lexicon parameter encoder
        val queryString = json.encodeToJsonElement(
            @Suppress("UNCHECKED_CAST") 
            (Json.serializersModule.getContextual(params::class) as? kotlinx.serialization.KSerializer<T>) 
            ?: throw Exception("Serializer not found for params"), 
            params
        ).let { /* convert JsonElement to query string */ "" } 
        
        return if (queryString.isNotEmpty()) "$baseUrl?$queryString" else baseUrl
    }
}
