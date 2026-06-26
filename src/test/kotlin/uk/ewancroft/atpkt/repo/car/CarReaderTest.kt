package uk.ewancroft.atpkt.repo.car

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import uk.ewancroft.atpkt.repo.cbor.CborArray
import uk.ewancroft.atpkt.repo.cbor.CborEncoder
import uk.ewancroft.atpkt.repo.cbor.CborInt
import uk.ewancroft.atpkt.repo.cbor.CborMap
import uk.ewancroft.atpkt.repo.cid.CidUtil
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class CarReaderTest : DescribeSpec({
    describe("CarReader") {
        it("should read CAR headers and blocks") {
            val encoder = CborEncoder()
            val blockBytes = byteArrayOf(0xa0.toByte())
            val blockCid = CidUtil.computeCid(blockBytes)
            val headerBytes = encoder.encode(
                CborMap(
                    mapOf(
                        "version" to CborInt(1),
                        "roots" to CborArray(listOf(encoder.cidLink(blockCid)))
                    )
                )
            )

            val carBytes = ByteArrayOutputStream().apply {
                writeVarint(headerBytes.size.toLong())
                write(headerBytes)
                val cidBytes = CidUtil.decodeCidBytes(blockCid)
                val blockPayload = cidBytes + blockBytes
                writeVarint(blockPayload.size.toLong())
                write(blockPayload)
            }.toByteArray()

            val reader = CarReader(ByteArrayInputStream(carBytes))

            reader.readHeader() shouldBe CarHeader(version = 1, roots = listOf(blockCid))
            reader.readNextBlock() shouldBe CarBlock(cid = blockCid, bytes = blockBytes)
            reader.readNextBlock() shouldBe null
        }
    }
})

private fun ByteArrayOutputStream.writeVarint(value: Long) {
    var remaining = value
    do {
        var current = (remaining and 0x7f).toInt()
        remaining = remaining ushr 7
        if (remaining != 0L) {
            current = current or 0x80
        }
        write(current)
    } while (remaining != 0L)
}
