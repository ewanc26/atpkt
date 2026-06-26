package uk.ewancroft.atpkt.oauth.storage

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking

class InMemorySessionStoreTest : DescribeSpec({
    describe("InMemorySessionStore") {
        it("should set, get and delete sessions") {
            runBlocking {
                val store = InMemorySessionStore()

                store.get("did:plc:missing") shouldBe null

                store.set("did:plc:123", "session-json")
                store.get("did:plc:123") shouldBe "session-json"

                store.del("did:plc:123")
                store.get("did:plc:123") shouldBe null
            }
        }

        it("should overwrite existing sessions") {
            runBlocking {
                val store = InMemorySessionStore()

                store.set("did:plc:123", "session-json-1")
                store.set("did:plc:123", "session-json-2")

                store.get("did:plc:123") shouldBe "session-json-2"
                store.list() shouldBe listOf("did:plc:123")
            }
        }
    }
})
