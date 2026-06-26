package uk.ewancroft.atpkt.repo.mst

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class MstNodeTest : DescribeSpec({
    describe("MstNode") {
        it("computes a stable root CID for the same tree") {
            val node = MstNode(
                cid = "bafyroot",
                entries = listOf(
                    MstEntry(key = "app.bsky.feed.post/3k", cid = "bafychild")
                )
            )

            val firstRootCid = node.computeRootCid()
            val secondRootCid = node.computeRootCid()

            firstRootCid shouldBe secondRootCid
            firstRootCid.startsWith("b") shouldBe true
        }

        it("changes when the tree contents change") {
            val baseNode = MstNode(
                cid = "bafyroot",
                entries = listOf(MstEntry(key = "app.bsky.feed.post/3k", cid = "bafychild"))
            )
            val changedNode = baseNode.copy(
                entries = baseNode.entries + MstEntry(key = "app.bsky.feed.post/3l", cid = "bafyanother")
            )

            baseNode.computeRootCid() shouldNotBe changedNode.computeRootCid()
        }
    }
})
