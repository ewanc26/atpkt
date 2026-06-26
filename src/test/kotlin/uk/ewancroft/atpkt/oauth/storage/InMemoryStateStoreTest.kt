package uk.ewancroft.atpkt.oauth.storage

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

class InMemoryStateStoreTest : DescribeSpec({
    describe("InMemoryStateStore") {
        it("should set, get and delete states") {
            runBlocking {
                val store = InMemoryStateStore()

                store.get("state-missing") shouldBe null

                store.set("state-123", "code-verifier-abc")
                store.get("state-123") shouldBe "code-verifier-abc"

                store.del("state-123")
                store.get("state-123") shouldBe null
            }
        }

        it("should expire states after the configured TTL") {
            runBlocking {
                val store = InMemoryStateStore(ttl = 100.milliseconds)

                store.set("state-123", "code-verifier-abc")
                delay(150)

                store.get("state-123") shouldBe null
            }
        }
    }
})
