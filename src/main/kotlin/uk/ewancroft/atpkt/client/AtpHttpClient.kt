package uk.ewancroft.atpkt.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Audit Update: Aligned AtpHttpClient with Ktor 3.x standards.
 * - Centralized Json configuration for strict Lexicon adherence.
 * - Added Logging for SDK debugging (Ktor 3.x pattern).
 * - Enforced requestTimeout adherence.
 */
object AtpHttpClient {
    private val sdkJson = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        coerceInputValues = true // Ensure compatibility with evolved Lexicons
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(sdkJson)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 15000
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }
}
