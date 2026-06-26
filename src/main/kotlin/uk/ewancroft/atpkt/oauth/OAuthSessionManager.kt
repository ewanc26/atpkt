package uk.ewancroft.atpkt.oauth

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import uk.ewancroft.atpkt.oauth.storage.SessionStore
import java.security.KeyPair
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

// ── OAuth session persistence and refresh ──────────

@Serializable
data class OAuthSession(
    val did: String,
    val accessToken: String,
    val refreshToken: String?,
    val tokenEndpoint: String,
    val clientId: String,
    val dpopKeyPairSerialized: SerializedKeyPair
)

@Serializable
data class SerializedKeyPair(
    val publicKey: String,
    val privateKey: String
)

class OAuthSessionManager(
    private val oauthClient: OAuthClient,
    val sessionStore: SessionStore
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun saveSession(session: OAuthSession) {
        val serialized = json.encodeToString(session)
        sessionStore.set(session.did, serialized)
    }

    suspend fun getSession(did: String): OAuthSession? {
        val serialized = sessionStore.get(did) ?: return null
        return json.decodeFromString<OAuthSession>(serialized)
    }

    suspend fun refreshSession(did: String): Result<OAuthSession> = runCatching {
        val session = getSession(did) ?: throw Exception("Session not found for $did")
        val refreshToken = session.refreshToken ?: throw Exception("No refresh token available")

        val keyPair = deserializeKeyPair(session.dpopKeyPairSerialized)
        
        val tokenResponse = oauthClient.refreshToken(
            refreshToken = refreshToken,
            clientId = session.clientId,
            tokenEndpoint = session.tokenEndpoint,
            dpopKeyPair = keyPair
        ).getOrThrow()

        val updatedSession = session.copy(
            accessToken = tokenResponse.accessToken,
            refreshToken = tokenResponse.refreshToken ?: session.refreshToken
        )

        saveSession(updatedSession)
        updatedSession
    }

    private fun deserializeKeyPair(serialized: SerializedKeyPair): KeyPair {
        val kf = KeyFactory.getInstance("EC")
        val publicBytes = Base64.getDecoder().decode(serialized.publicKey)
        val privateBytes = Base64.getDecoder().decode(serialized.privateKey)
        
        val publicKey = kf.generatePublic(X509EncodedKeySpec(publicBytes))
        val privateKey = kf.generatePrivate(PKCS8EncodedKeySpec(privateBytes))
        
        return KeyPair(publicKey, privateKey)
    }
}
