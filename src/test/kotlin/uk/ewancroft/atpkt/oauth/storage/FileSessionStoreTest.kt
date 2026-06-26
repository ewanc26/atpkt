package uk.ewancroft.atpkt.oauth.storage

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import java.nio.file.Files

class FileSessionStoreTest : DescribeSpec({
    describe("FileSessionStore") {
        it("should persist sessions across store instances") {
            runBlocking {
                val directory = Files.createTempDirectory("atpkt-session-store")
                val did = "did:plc:123"
                val sessionJson = """{"did":"$did","accessToken":"abc","refreshToken":"def","tokenEndpoint":"https://token.example.com","clientId":"https://client.example.com","dpopKeyPairSerialized":{"publicKey":"pub","privateKey":"priv"}}"""
                val sessionFile = directory.resolve("$did.json")

                try {
                    val firstStore = FileSessionStore(directory)
                    firstStore.set(did, sessionJson)

                    Files.exists(sessionFile) shouldBe true
                    Files.readString(sessionFile) shouldBe sessionJson
                    firstStore.list() shouldBe listOf(did)

                    val restartedStore = FileSessionStore(directory)
                    restartedStore.get(did) shouldBe sessionJson
                    restartedStore.list() shouldBe listOf(did)

                    restartedStore.del(did)
                    Files.exists(sessionFile) shouldBe false
                } finally {
                    directory.toFile().deleteRecursively()
                }
            }
        }
    }
})
