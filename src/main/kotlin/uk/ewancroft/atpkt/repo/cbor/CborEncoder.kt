package uk.ewancroft.atpkt.repo.cbor

import uk.ewancroft.atpkt.repo.cid.CidUtil
import java.io.ByteArrayOutputStream
import kotlin.math.min

/**
 * Deterministic CBOR value model used for DAG-CBOR encoding.
 */
sealed interface DagCborValue

data object CborNull : DagCborValue

data class CborBoolean(val value: Boolean) : DagCborValue

data class CborInt(val value: Long) : DagCborValue

data class CborString(val value: String) : DagCborValue

data class CborBytes(val value: ByteArray) : DagCborValue {
    override fun equals(other: Any?): Boolean = other is CborBytes && value.contentEquals(other.value)
    override fun hashCode(): Int = value.contentHashCode()
}

data class CborArray(val values: List<DagCborValue>) : DagCborValue

data class CborMap(val values: Map<String, DagCborValue>) : DagCborValue

data class CborTag(val tag: Long, val value: DagCborValue) : DagCborValue

/**
 * Encodes a restricted Kotlin value model as deterministic DAG-CBOR.
 *
 * Supported values:
 * - null
 * - booleans
 * - signed integers
 * - strings
 * - byte arrays
 * - lists
 * - maps with string keys
 * - semantic tags, including CID tag 42
 */
class CborEncoder {
    fun encode(value: DagCborValue): ByteArray {
        val output = ByteArrayOutputStream()
        writeValue(output, value)
        return output.toByteArray()
    }

    fun cidLink(cid: String): DagCborValue {
        val cidBytes = byteArrayOf(0x00) + CidUtil.decodeCidBytes(cid)
        return CborTag(42, CborBytes(cidBytes))
    }

    private fun writeValue(output: ByteArrayOutputStream, value: DagCborValue) {
        when (value) {
            CborNull -> output.write(0xf6)
            is CborBoolean -> output.write(if (value.value) 0xf5 else 0xf4)
            is CborInt -> writeInt(output, value.value)
            is CborString -> writeTextString(output, value.value)
            is CborBytes -> writeByteString(output, value.value)
            is CborArray -> {
                writeMajorAndLength(output, major = 4, value.values.size.toLong())
                value.values.forEach { writeValue(output, it) }
            }
            is CborMap -> writeMap(output, value.values)
            is CborTag -> {
                writeMajorAndLength(output, major = 6, value.tag)
                writeValue(output, value.value)
            }
        }
    }

    private fun writeMap(output: ByteArrayOutputStream, values: Map<String, DagCborValue>) {
        val sortedEntries = values.entries
            .map { entry -> Triple(entry.key, entry.value, encodeTextString(entry.key)) }
            .sortedWith(java.util.Comparator { left, right -> compareEncodedKeys(left.third, right.third) })

        writeMajorAndLength(output, major = 5, value = sortedEntries.size.toLong())
        for ((key, value, _) in sortedEntries) {
            writeTextString(output, key)
            writeValue(output, value)
        }
    }

    private fun compareEncodedKeys(left: ByteArray, right: ByteArray): Int {
        val lengthComparison = left.size.compareTo(right.size)
        if (lengthComparison != 0) {
            return lengthComparison
        }

        val limit = min(left.size, right.size)
        for (index in 0 until limit) {
            val byteComparison = (left[index].toInt() and 0xff).compareTo(right[index].toInt() and 0xff)
            if (byteComparison != 0) {
                return byteComparison
            }
        }

        return 0
    }

    private fun encodeTextString(value: String): ByteArray {
        val output = ByteArrayOutputStream()
        writeTextString(output, value)
        return output.toByteArray()
    }

    private fun writeTextString(output: ByteArrayOutputStream, value: String) {
        val bytes = value.toByteArray(Charsets.UTF_8)
        writeMajorAndLength(output, major = 3, value = bytes.size.toLong())
        output.write(bytes)
    }

    private fun writeByteString(output: ByteArrayOutputStream, value: ByteArray) {
        writeMajorAndLength(output, major = 2, value = value.size.toLong())
        output.write(value)
    }

    private fun writeInt(output: ByteArrayOutputStream, value: Long) {
        if (value >= 0) {
            writeMajorAndLength(output, major = 0, value = value)
            return
        }

        writeMajorAndLength(output, major = 1, value = -1L - value)
    }

    private fun writeMajorAndLength(output: ByteArrayOutputStream, major: Int, value: Long) {
        require(value >= 0) { "CBOR lengths and unsigned values must be non-negative" }
        when {
            value < 24 -> output.write((major shl 5) or value.toInt())
            value <= 0xff -> {
                output.write((major shl 5) or 24)
                output.write(value.toInt() and 0xff)
            }
            value <= 0xffff -> {
                output.write((major shl 5) or 25)
                writeBigEndian(output, value, 2)
            }
            value <= 0xffff_ffffL -> {
                output.write((major shl 5) or 26)
                writeBigEndian(output, value, 4)
            }
            else -> {
                output.write((major shl 5) or 27)
                writeBigEndian(output, value, 8)
            }
        }
    }

    private fun writeBigEndian(output: ByteArrayOutputStream, value: Long, byteCount: Int) {
        for (shift in (byteCount - 1) downTo 0) {
            output.write((value shr (shift * 8)).toInt() and 0xff)
        }
    }
}

/**
 * Minimal deterministic CBOR decoder for the supported DAG-CBOR subset.
 */
class CborDecoder {
    fun decode(bytes: ByteArray): DagCborValue {
        val cursor = Cursor(bytes)
        val value = readValue(cursor)
        require(cursor.index == bytes.size) { "Trailing bytes found after CBOR value" }
        return value
    }

    private fun readValue(cursor: Cursor): DagCborValue {
        val initial = cursor.readByte()
        val major = initial ushr 5
        val additional = initial and 0x1f

        return when (major) {
            0 -> CborInt(readLength(cursor, additional))
            1 -> CborInt(-1L - readLength(cursor, additional))
            2 -> CborBytes(cursor.readBytes(readLength(cursor, additional).toInt()))
            3 -> CborString(cursor.readBytes(readLength(cursor, additional).toInt()).toString(Charsets.UTF_8))
            4 -> {
                val length = readLength(cursor, additional).toInt()
                CborArray(List(length) { readValue(cursor) })
            }
            5 -> {
                val length = readLength(cursor, additional).toInt()
                val values = linkedMapOf<String, DagCborValue>()
                repeat(length) {
                    val key = readValue(cursor)
                    require(key is CborString) { "DAG-CBOR map keys must be text strings" }
                    values[key.value] = readValue(cursor)
                }
                CborMap(values)
            }
            6 -> {
                val tag = readLength(cursor, additional)
                CborTag(tag, readValue(cursor))
            }
            7 -> when (additional) {
                20 -> CborBoolean(false)
                21 -> CborBoolean(true)
                22 -> CborNull
                23 -> error("undefined is not a supported atproto CBOR value")
                else -> error("Unsupported CBOR simple value: $additional")
            }
            else -> error("Unsupported CBOR major type: $major")
        }
    }

    private fun readLength(cursor: Cursor, additional: Int): Long {
        return when (additional) {
            in 0..23 -> additional.toLong()
            24 -> cursor.readByte().toLong() and 0xffL
            25 -> cursor.readUInt(2)
            26 -> cursor.readUInt(4)
            27 -> cursor.readUInt(8)
            else -> error("Indefinite-length CBOR items are not supported")
        }
    }

    private class Cursor(private val bytes: ByteArray) {
        var index: Int = 0
            private set

        fun readByte(): Int {
            require(index < bytes.size) { "Unexpected end of CBOR data" }
            return bytes[index++].toInt() and 0xff
        }

        fun readBytes(length: Int): ByteArray {
            require(length >= 0) { "Length must be non-negative" }
            require(index + length <= bytes.size) { "Unexpected end of CBOR data" }
            val slice = bytes.copyOfRange(index, index + length)
            index += length
            return slice
        }

        fun readUInt(byteCount: Int): Long {
            require(byteCount in setOf(2, 4, 8)) { "Unsupported integer byte count" }
            var value = 0L
            repeat(byteCount) {
                value = (value shl 8) or (readByte().toLong() and 0xffL)
            }
            return value
        }
    }
}
