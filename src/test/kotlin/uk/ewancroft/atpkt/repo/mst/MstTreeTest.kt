package uk.ewancroft.atpkt.repo.mst

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import uk.ewancroft.atpkt.repo.cid.CidUtil

class MstTreeTest : DescribeSpec({
    val recordA = CidUtil.computeCid(byteArrayOf(0x01))
    val recordB = CidUtil.computeCid(byteArrayOf(0x02))
    val recordC = CidUtil.computeCid(byteArrayOf(0x03))

    describe("MstTree") {
        it("should insert, fetch, list and delete entries") {
            val tree = MstTree()
                .insert("app.bsky.feed.post/1", recordA)
                .insert("app.bsky.feed.post/2", recordB)

            tree.get("app.bsky.feed.post/1") shouldBe recordA
            tree.get("app.bsky.feed.post/2") shouldBe recordB
            tree.list() shouldBe listOf(
                MstEntry("app.bsky.feed.post/1", recordA),
                MstEntry("app.bsky.feed.post/2", recordB)
            )

            tree.delete("app.bsky.feed.post/1").get("app.bsky.feed.post/1") shouldBe null
        }

        it("should produce a stable root CID for the same content") {
            val left = MstTree(
                listOf(
                    MstEntry("app.bsky.feed.post/1", recordA),
                    MstEntry("app.bsky.feed.post/2", recordB)
                )
            )
            val right = MstTree(
                listOf(
                    MstEntry("app.bsky.feed.post/1", recordA),
                    MstEntry("app.bsky.feed.post/2", recordB)
                )
            )

            left.rootCid() shouldBe right.rootCid()
            left.rootNode().computeRootCid() shouldBe left.rootCid()
        }

        it("should diff additions, removals and updates") {
            val before = MstTree(
                listOf(
                    MstEntry("app.bsky.feed.post/1", recordA),
                    MstEntry("app.bsky.feed.post/2", recordB)
                )
            )
            val after = MstTree(
                listOf(
                    MstEntry("app.bsky.feed.post/2", recordC),
                    MstEntry("app.bsky.feed.post/3", recordC)
                )
            )

            before.diff(after) shouldBe MstDiff(
                added = listOf(MstEntry("app.bsky.feed.post/3", recordC)),
                removed = listOf(MstEntry("app.bsky.feed.post/1", recordA)),
                changed = listOf(MstEntryChange("app.bsky.feed.post/2", recordB, recordC))
            )
        }
    }
})
