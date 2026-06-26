package uk.ewancroft.atpkt.oauth

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.runBlocking
import uk.ewancroft.atpkt.crypto.CryptoUtil
import uk.ewancroft.atpkt.oauth.storage.SessionStore
import java.security.KeyPair
import java.util.Base64

class OAuthSessionManagerTest : DescribeSpec({
    describe("OAuthSessionManager") {
        it("saves and reloads sessions through the store") {
            val store = InMemorySessionStore()
            val manager = OAuthSessionManager(mockk(), store)
            val session = OAuthSession(
                did = "did:plc:save-test",
                accessToken = "access-1",
                refreshToken = "refresh-1",
                tokenEndpoint = "https://example.com/token",
                clientId = "test-client-id",
                dpopKeyPairSerialized = SerializedKeyPair("public-key", "private-key")
            )

            runBlocking { manager.saveSession(session) }
            val retrieved = runBlocking { manager.getSession("did:plc:save-test") }

            retrieved shouldBe session
        }

        it("refreshes a session using the OAuth client and persists the new tokens") {
            val store = InMemorySessionStore()
            val oauthClient = mockk<OAuthClient>()
            val manager = OAuthSessionManager(oauthClient, store)
            val keyPair = CryptoUtil.generateKeyPair()
            val did = "did:plc:refresh-test"
            val session = OAuthSession(
                did = did,
                accessToken = "old-access",
                refreshToken = "old-refresh",
                tokenEndpoint = "https://example.com/token",
                clientId = "test-client",
                dpopKeyPairSerialized = serialiseKeyPair(keyPair)
            )

            runBlocking { manager.saveSession(session) }

            coEvery {
                oauthClient.refreshToken(
                    refreshToken = "old-refresh",
                    clientId = "test-client",
                    tokenEndpoint = "https://example.com/token",
                    dpopKeyPair = any(),
                    authServerNonce = null
                )
            } returns Result.success(
                TokenResponse(
                    accessToken = "new-access",
                    refreshToken = "new-refresh",
                    tokenType = "DPoP",
                    expiresIn = 3600
                )
            )

            val refreshed = runBlocking { manager.refreshSession(did) }.getOrThrow()
            val stored = runBlocking { manager.getSession(did) }

            refreshed.accessToken shouldBe "new-access"
            refreshed.refreshToken shouldBe "new-refresh"
            stored shouldBe refreshed
        }

        it("fails when refreshing an unknown session") {
            val manager = OAuthSessionManager(mockk(), InMemorySessionStore())

            val result = runBlocking { manager.refreshSession("did:plc:missing") }

            result.isFailure shouldBe true
            result.exceptionOrNull()?.message shouldBe "Session not found for did:plc:missing"
        }
    }
})

private class InMemorySessionStore : SessionStore {
    private val values = mutableMapOf<String, String>()

    override suspend fun get(key: String): String? = values[key]

    override suspend fun set(key: String, value: String) {
        values[key] = value
    }

    override suspend fun del(key: String) {
        values.remove(key)
    }
}

private fun serialiseKeyPair(keyPair: KeyPair): SerializedKeyPair {
    val encoder = Base64.getEncoder()
    return SerializedKeyPair(
        publicKey = encoder.encodeToString(keyPair.public.encoded),
        privateKey = encoder.encodeToString(keyPair.private.encoded)
    )
}
