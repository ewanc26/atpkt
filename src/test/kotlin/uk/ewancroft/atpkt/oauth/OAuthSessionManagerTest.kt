package uk.ewancroft.atpkt.oauth

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uk.ewancroft.atpkt.oauth.storage.SessionStore
import java.security.KeyPairGenerator
import java.util.Base64

class OAuthSessionManagerTest : DescribeSpec({
    val mockStore = mockk<SessionStore>()
    val mockClient = mockk<OAuthClient>()
    val manager = OAuthSessionManager(mockClient, mockStore)
    val json = Json { ignoreUnknownKeys = true }

    describe("OAuthSessionManager") {
        it("should save and retrieve a session via SessionStore") {
            val store = object : SessionStore {
                private val map = mutableMapOf<String, String>()
                override suspend fun get(key: String) = map[key]
                override suspend fun set(key: String, value: String) { map[key] = value }
                override suspend fun del(key: String) { map.remove(key) }
            }
            val realManager = OAuthSessionManager(mockClient, store)

            val session = OAuthSession(
                did = "did:plc:save-test",
                accessToken = "access-1",
                refreshToken = "refresh-1",
                tokenEndpoint = "https://example.com/token",
                clientId = "test-client-id",
                dpopKeyPairSerialized = SerializedKeyPair("pub-key", "priv-key")
            )

            runBlocking { realManager.saveSession(session) }
            val retrieved = runBlocking { realManager.getSession("did:plc:save-test") }

            retrieved shouldNotBe null
            retrieved?.did shouldBe "did:plc:save-test"
            retrieved?.accessToken shouldBe "access-1"
            retrieved?.refreshToken shouldBe "refresh-1"
        }

        it("should retrieve a session from the store") {
            val did = "did:plc:123"
            val sessionJson = """{"did":"$did","accessToken":"abc","refreshToken":"def","tokenEndpoint":"url","clientId":"id","dpopKeyPairSerialized":{"publicKey":"pub","privateKey":"priv"}}"""

            coEvery { mockStore.get(did) } returns sessionJson

            val session = runBlocking { manager.getSession(did) }
            session?.did shouldBe did
            session?.accessToken shouldBe "abc"
        }

        it("should return null if no session exists") {
            coEvery { mockStore.get(any()) } returns null
            runBlocking { manager.getSession("non-existent") } shouldBe null
        }

        it("should refresh a session via OAuthClient") {
            val kpGen = KeyPairGenerator.getInstance("EC")
            kpGen.initialize(256)
            val keyPair = kpGen.generateKeyPair()

            val pubEncoded = Base64.getEncoder().encodeToString(keyPair.public.encoded)
            val privEncoded = Base64.getEncoder().encodeToString(keyPair.private.encoded)

            val did = "did:plc:refresh-test"
            val session = OAuthSession(
                did = did,
                accessToken = "old-access",
                refreshToken = "old-refresh",
                tokenEndpoint = "https://example.com/token",
                clientId = "test-client",
                dpopKeyPairSerialized = SerializedKeyPair(pubEncoded, privEncoded)
            )
            val sessionJson = json.encodeToString(session)

            coEvery { mockStore.get(did) } returns sessionJson
            coEvery { mockStore.set(any(), any()) } returns Unit
            coEvery {
                mockClient.refreshToken(
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

            val result = runBlocking { manager.refreshSession(did) }

            result.isSuccess shouldBe true
            result.getOrNull()?.accessToken shouldBe "new-access"
            result.getOrNull()?.refreshToken shouldBe "new-refresh"

            coVerify { mockStore.set(did, any()) }
        }
    }
})
