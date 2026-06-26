package uk.ewancroft.atpkt.repo.commit

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import uk.ewancroft.atpkt.crypto.CryptoUtil
import uk.ewancroft.atpkt.repo.cid.CidUtil

class CommitManagerTest : DescribeSpec({
    val keyPair = CryptoUtil.generateKeyPair()
    val recordCid = CidUtil.computeCid(byteArrayOf(0x01))
    val mstRootCid = CidUtil.computeCid(byteArrayOf(0x02))

    describe("CommitManager") {
        it("should sign and verify a commit") {
            val unsigned = UnsignedCommit(
                did = "did:plc:example",
                rev = "01HZY1XG1A0000000000000000",
                prev = null,
                data = mstRootCid
            )

            val commit = CommitManager.sign(unsigned, keyPair.private)

            commit.did shouldBe unsigned.did
            commit.data shouldBe unsigned.data
            CommitManager.verify(commit, keyPair.public) shouldBe true
            commit.cid() shouldBe CommitManager.cid(commit)
        }

        it("should reject mutated commits") {
            val unsigned = UnsignedCommit(
                did = "did:plc:example",
                rev = "01HZY1XG1A0000000000000000",
                prev = null,
                data = mstRootCid
            )
            val commit = CommitManager.sign(unsigned, keyPair.private)
            CommitManager.verify(commit.copy(data = recordCid), keyPair.public) shouldBe false
        }

        it("should chain commits through prev pointers") {
            val session = CommitManager.startSession(keyPair.private, keyPair.public)

            val first = session.createCommit(
                did = "did:plc:example",
                rev = "01HZY1XG1A0000000000000000",
                data = mstRootCid
            )
            val second = session.createCommit(
                did = "did:plc:example",
                rev = "01HZY1XG1B0000000000000000",
                data = mstRootCid
            )

            first.prev shouldBe null
            second.prev shouldBe first.cid()
            session.history() shouldBe listOf(first, second)
            session.latest() shouldBe second
            session.verify(first) shouldBe true
            session.verify(second) shouldBe true
        }
    }
})
