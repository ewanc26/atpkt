package uk.ewancroft.atpkt.repo.cid

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class CidUtilTest : DescribeSpec({
    describe("CidUtil") {
        it("computes the expected CID for an empty payload") {
            val cid = CidUtil.computeCid(byteArrayOf())

            cid shouldBe "bafyreihdwdcefgh4dqkjv67uzcmw7ojee6xedzdetojuzjevtenxquvyku"
            cid.startsWith("b") shouldBe true
            decodeCid(cid).copyOfRange(0, 4).contentEquals(byteArrayOf(0x01, 0x71, 0x12, 0x20)) shouldBe true
        }

        it("computes a known CID for hello") {
            val cid = CidUtil.computeCid("hello".encodeToByteArray())

            cid shouldBe "bafyreibm6jg3ux5qumhcn2b3flc3tyu6dmlb4xa7u5bf44yegnrjhc4yeq"
            cid.startsWith("b") shouldBe true
            decodeCid(cid).copyOfRange(0, 4).contentEquals(byteArrayOf(0x01, 0x71, 0x12, 0x20)) shouldBe true
        }

        it("produces different identifiers for different payloads") {
            val first = CidUtil.computeCid("alpha".encodeToByteArray())
            val second = CidUtil.computeCid("beta".encodeToByteArray())

            first shouldNotBe second
        }
    }
})

private fun decodeCid(cid: String): ByteArray {
    require(cid.startsWith("b")) { "Expected a multibase base32 CID" }

    val alphabet = "abcdefghijklmnopqrstuvwxyz234567"
    val lookup = alphabet.withIndex().associate { it.value to it.index }
    val output = ArrayList<Byte>()
    var buffer = 0
    var bitsLeft = 0

    for (character in cid.drop(1)) {
        val value = lookup[character] ?: error("Unexpected base32 character: $character")
        buffer = (buffer shl 5) or value
        bitsLeft += 5

        while (bitsLeft >= 8) {
            bitsLeft -= 8
            output.add(((buffer shr bitsLeft) and 0xFF).toByte())
        }
    }

    return output.toByteArray()
}
