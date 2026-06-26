package uk.ewancroft.atpkt.repo.cbor

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CborEncoderTest : DescribeSpec({
    val encoder = CborEncoder()
    val decoder = CborDecoder()

    describe("CborEncoder") {
        it("should encode primitive values deterministically") {
            encoder.encode(CborInt(-1)).toHex() shouldBe "20"
            encoder.encode(CborBoolean(true)).toHex() shouldBe "f5"
            encoder.encode(CborNull).toHex() shouldBe "f6"
            encoder.encode(CborString("ok")).toHex() shouldBe "626f6b"
        }

        it("should encode arrays in insertion order") {
            val encoded = encoder.encode(
                CborArray(
                    listOf(
                        CborBoolean(true),
                        CborNull,
                        CborString("ok")
                    )
                )
            )

            encoded.toHex() shouldBe "83f5f6626f6b"
            decoder.decode(encoded) shouldBe CborArray(
                listOf(
                    CborBoolean(true),
                    CborNull,
                    CborString("ok")
                )
            )
        }

        it("should encode map keys using canonical ordering") {
            val encoded = encoder.encode(
                CborMap(
                    mapOf(
                        "aa" to CborInt(2),
                        "b" to CborInt(1)
                    )
                )
            )

            encoded.toHex() shouldBe "a261620162616102"
        }

        it("should encode CID tag 42 as a tagged byte string") {
            val encoded = encoder.encode(CborTag(42, CborBytes(byteArrayOf(0x00, 0x01))))
            encoded.toHex() shouldBe "d82a420001"
        }
    }
})

private fun ByteArray.toHex(): String = joinToString(separator = "") { byte -> "%02x".format(byte) }
