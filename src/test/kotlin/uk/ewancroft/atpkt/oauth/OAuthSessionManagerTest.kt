package uk.ewancroft.atpkt.oauth

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import uk.ewancroft.atpkt.oauth.storage.SessionStore

// Tests for OAuth session persistence and retrieval via SessionStore
class OAuthSessionManagerTest : DescribeSpec({
    val mockStore = mockk<SessionStore>()
    val mockClient = mockk<OAuthClient>()
    val manager = OAuthSessionManager(mockClient, mockStore)

    describe("OAuthSessionManager") {
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
    }
})
