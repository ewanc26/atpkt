package uk.ewancroft.atpkt.repo.storage

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class InMemoryBlockstoreTest : DescribeSpec({
    describe("InMemoryBlockstore") {
        it("stores and retrieves blocks by CID") {
            val blockstore = InMemoryBlockstore()
            val cid = "bafyreiblockstoretest"
            val bytes = "hello blockstore".encodeToByteArray()

            blockstore.put(cid, bytes)

            blockstore.has(cid) shouldBe true
            blockstore.getBytes(cid)?.decodeToString() shouldBe "hello blockstore"
            blockstore.sizeInBytes() shouldBe bytes.size.toLong()
        }

        it("clears all blocks when destroyed") {
            val blockstore = InMemoryBlockstore()
            blockstore.put("cid-1", "one".encodeToByteArray())
            blockstore.put("cid-2", "two".encodeToByteArray())

            blockstore.destroy()

            blockstore.has("cid-1") shouldBe false
            blockstore.has("cid-2") shouldBe false
            blockstore.sizeInBytes() shouldBe 0L
        }
    }
})
