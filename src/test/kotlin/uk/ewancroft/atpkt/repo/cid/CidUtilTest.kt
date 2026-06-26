package uk.ewancroft.atpkt.repo.cid

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CidUtilTest : DescribeSpec({
    describe("CidUtil") {
        it("should compute the expected CIDv1 for an empty DAG-CBOR map") {
            CidUtil.computeCid(byteArrayOf(0xa0.toByte())) shouldBe "bafyreigbtj4x7ip5legnfznufuopl4sg4knzc2cof6duas4b3q2fy6swua"
        }

        it("should round-trip CID bytes through multibase encoding") {
            val cid = CidUtil.computeCid(byteArrayOf(0xa0.toByte()))
            CidUtil.encodeCidBytes(CidUtil.decodeCidBytes(cid)) shouldBe cid
        }
    }
})
