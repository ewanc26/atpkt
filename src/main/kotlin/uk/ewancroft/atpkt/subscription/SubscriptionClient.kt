package uk.ewancroft.atpkt.subscription

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.cbor.Cbor
import java.net.URI

/**
 * Base subscription client for AT Protocol firehose streams.
 */
@OptIn(ExperimentalSerializationApi::class)
class SubscriptionClient(private val pdsUrl: String) {
    private val client = HttpClient(CIO) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Cbor { })
        }
    }

    /**
     * Connects to a firehose subscription and streams events as a Flow.
     */
    fun subscribe(endpoint: String, accessJwt: String? = null): Flow<ByteArray> = flow {
        val uri = URI.create(pdsUrl.replace("https://", "wss://").replace("http://", "ws://") + "/xrpc/" + endpoint)

        client.webSocket(
            urlString = uri.toString(),
            request = {
                accessJwt?.let { header("Authorization", "Bearer $it") }
            }
        ) {
            for (frame in incoming) {
                if (frame is Frame.Binary) {
                    emit(frame.readBytes())
                }
            }
        }
    }
}
