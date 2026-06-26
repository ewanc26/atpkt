package uk.ewancroft.atpkt.repo.car

import uk.ewancroft.atpkt.repo.cbor.CborArray
import uk.ewancroft.atpkt.repo.cbor.CborBytes
import uk.ewancroft.atpkt.repo.cbor.CborDecoder
import uk.ewancroft.atpkt.repo.cbor.CborInt
import uk.ewancroft.atpkt.repo.cbor.CborMap
import uk.ewancroft.atpkt.repo.cbor.CborString
import uk.ewancroft.atpkt.repo.cbor.CborTag
import uk.ewancroft.atpkt.repo.cbor.DagCborValue
import uk.ewancroft.atpkt.repo.cid.CidUtil
import java.io.InputStream

/**
 * CAR v1 header as defined by the IPLD transport specification.
 */
data class CarHeader(
    val version: Long,
    val roots: List<String>
)

/**
 * A CAR block (CID + raw bytes).
 */
data class CarBlock(
    val cid: String,
    val bytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CarBlock) return false
        return cid == other.cid && bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        var result = cid.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}

/**
 * Parsed CAR archive.
 */
data class CarArchive(
    val header: CarHeader,
    val blocks: List<CarBlock>
)

/**
 * CAR (Content Addressable aRchive) reader.
 */
class CarReader(private val inputStream: InputStream) {
    private val decoder = CborDecoder()
    private var header: CarHeader? = null

    fun readHeader(): CarHeader {
        header?.let { return it }

        val headerBytes = readLengthPrefixedBytes()
            ?: error("CAR archive is missing a header")
        val headerValue = decoder.decode(headerBytes)
        val parsedHeader = parseHeader(headerValue)
        header = parsedHeader
        return parsedHeader
    }

    fun readNextBlock(): CarBlock? {
        if (header == null) {
            readHeader()
        }

        val payload = readLengthPrefixedBytes() ?: return null
        val cidLength = CidUtil.cidBinaryLength(payload)
        val cidBytes = payload.copyOfRange(0, cidLength)
        val blockBytes = payload.copyOfRange(cidLength, payload.size)
        return CarBlock(cid = CidUtil.encodeCidBytes(cidBytes), bytes = blockBytes)
    }

    fun readArchive(): CarArchive {
        val parsedHeader = readHeader()
        val blocks = mutableListOf<CarBlock>()
        while (true) {
            val block = readNextBlock() ?: break
            blocks += block
        }
        return CarArchive(header = parsedHeader, blocks = blocks)
    }

    /**
     * Backwards-compatible alias that returns the next block bytes, if any.
     */
    fun readNextFrame(): ByteArray? = readNextBlock()?.bytes

    private fun parseHeader(value: DagCborValue): CarHeader {
        val map = value as? CborMap ?: error("CAR header must be a CBOR map")

        val version = when (val versionValue = map.values["version"]) {
            is CborInt -> versionValue.value
            else -> error("CAR header version must be an integer")
        }

        val rootsValue = map.values["roots"] as? CborArray ?: error("CAR header roots must be an array")
        val roots = rootsValue.values.map { toCidString(it) }

        return CarHeader(version = version, roots = roots)
    }

    private fun toCidString(value: DagCborValue): String {
        return when (value) {
            is CborString -> value.value
            is CborTag -> {
                require(value.tag == 42L) { "Unexpected CAR header tag: ${value.tag}" }
                when (val nested = value.value) {
                    is CborBytes -> {
                        val bytes = nested.value
                        val cidBytes = if (bytes.isNotEmpty() && bytes[0] == 0x00.toByte()) {
                            bytes.copyOfRange(1, bytes.size)
                        } else {
                            bytes
                        }
                        CidUtil.encodeCidBytes(cidBytes)
                    }
                    else -> error("CAR CID tag must wrap byte string data")
                }
            }
            is CborBytes -> {
                val bytes = value.value
                val cidBytes = if (bytes.isNotEmpty() && bytes[0] == 0x00.toByte()) {
                    bytes.copyOfRange(1, bytes.size)
                } else {
                    bytes
                }
                CidUtil.encodeCidBytes(cidBytes)
            }
            else -> error("Unsupported CAR CID representation: ${value::class.simpleName}")
        }
    }

    private fun readLengthPrefixedBytes(): ByteArray? {
        val length = readUvarint() ?: return null
        require(length <= Int.MAX_VALUE.toLong()) { "CAR frame is too large" }
        val bytes = inputStream.readNBytes(length.toInt())
        require(bytes.size == length.toInt()) { "Unexpected end of CAR frame" }
        return bytes
    }

    private fun readUvarint(): Long? {
        var result = 0L
        var shift = 0
        var readAny = false

        while (true) {
            val next = inputStream.read()
            if (next == -1) {
                return if (readAny) error("Unexpected end of CAR varint") else null
            }
            readAny = true

            result = result or (((next and 0x7f).toLong()) shl shift)
            if (next and 0x80 == 0) {
                return result
            }

            shift += 7
            require(shift < 64) { "CAR varint is too large" }
        }
    }
}
