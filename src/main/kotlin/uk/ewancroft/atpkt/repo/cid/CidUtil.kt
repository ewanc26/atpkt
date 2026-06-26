package uk.ewancroft.atpkt.repo.cid

import java.io.ByteArrayOutputStream
import java.security.MessageDigest

/**
 * Content Identifier utilities aligned with the AT Protocol CID specification.
 */
object CidUtil {
    private const val CID_VERSION = 1L
    private const val DAG_CBOR_CODEC = 0x71L
    private const val RAW_CODEC = 0x55L
    private const val SHA256_CODE = 0x12L
    private const val SHA256_LENGTH = 0x20L
    private const val MULTIBASE_PREFIX = 'b'
    private const val BASE32_ALPHABET = "abcdefghijklmnopqrstuvwxyz234567"

    fun computeCid(data: ByteArray, codec: Long = DAG_CBOR_CODEC): String {
        require(codec >= 0) { "Codec must be non-negative" }

        val digest = MessageDigest.getInstance("SHA-256").digest(data)
        val output = ByteArrayOutputStream()
        output.writeBytes(encodeUvarint(CID_VERSION))
        output.writeBytes(encodeUvarint(codec))
        output.writeBytes(encodeUvarint(SHA256_CODE))
        output.writeBytes(encodeUvarint(SHA256_LENGTH))
        output.writeBytes(digest)

        return encodeCidBytes(output.toByteArray())
    }

    fun computeRawCid(data: ByteArray): String = computeCid(data, RAW_CODEC)

    fun encodeCidBytes(bytes: ByteArray): String {
        return MULTIBASE_PREFIX + encodeBase32Lower(bytes)
    }

    fun decodeCidBytes(cid: String): ByteArray {
        require(cid.isNotEmpty()) { "CID string must not be empty" }
        require(cid[0] == MULTIBASE_PREFIX) { "CID string must use the 'b' multibase prefix" }
        return decodeBase32Lower(cid.substring(1))
    }

    fun cidBinaryLength(cidBytes: ByteArray): Int {
        val (_, versionEnd) = decodeUvarint(cidBytes, 0)
        val (_, codecEnd) = decodeUvarint(cidBytes, versionEnd)
        val (_, hashCodeEnd) = decodeUvarint(cidBytes, codecEnd)
        val (hashLength, digestStart) = decodeUvarint(cidBytes, hashCodeEnd)
        val digestEnd = digestStart + hashLength.toInt()
        require(digestEnd <= cidBytes.size) { "CID bytes are truncated" }
        return digestEnd
    }

    private fun encodeBase32Lower(bytes: ByteArray): String {
        if (bytes.isEmpty()) {
            return ""
        }

        val output = StringBuilder((bytes.size * 8 + 4) / 5)
        var buffer = 0
        var bitsLeft = 0

        for (byte in bytes) {
            buffer = (buffer shl 8) or (byte.toInt() and 0xff)
            bitsLeft += 8

            while (bitsLeft >= 5) {
                val index = (buffer shr (bitsLeft - 5)) and 0x1f
                bitsLeft -= 5
                output.append(BASE32_ALPHABET[index])
            }
        }

        if (bitsLeft > 0) {
            val index = (buffer shl (5 - bitsLeft)) and 0x1f
            output.append(BASE32_ALPHABET[index])
        }

        return output.toString()
    }

    private fun decodeBase32Lower(value: String): ByteArray {
        if (value.isEmpty()) {
            return ByteArray(0)
        }

        val lookup = IntArray(128) { -1 }
        BASE32_ALPHABET.forEachIndexed { index, char ->
            lookup[char.code] = index
        }

        val output = mutableListOf<Byte>()
        var buffer = 0
        var bitsLeft = 0

        for (char in value.lowercase()) {
            require(char.code < lookup.size) { "Invalid base32 character: $char" }
            val chunk = lookup[char.code]
            require(chunk >= 0) { "Invalid base32 character: $char" }
            buffer = (buffer shl 5) or chunk
            bitsLeft += 5

            if (bitsLeft >= 8) {
                bitsLeft -= 8
                val byteValue = (buffer shr bitsLeft) and 0xff
                output.add(byteValue.toByte())
                buffer = buffer and ((1 shl bitsLeft) - 1)
            }
        }

        if (bitsLeft > 0) {
            val trailingBits = buffer and ((1 shl bitsLeft) - 1)
            require(trailingBits == 0) { "Base32 input contains non-zero trailing bits" }
        }

        return output.toByteArray()
    }

    private fun encodeUvarint(value: Long): ByteArray {
        require(value >= 0) { "Unsigned varints must be non-negative" }
        var remaining = value
        val output = mutableListOf<Byte>()
        do {
            var current = (remaining and 0x7f).toInt()
            remaining = remaining ushr 7
            if (remaining != 0L) {
                current = current or 0x80
            }
            output.add(current.toByte())
        } while (remaining != 0L)
        return output.toByteArray()
    }

    private fun decodeUvarint(bytes: ByteArray, offset: Int): Pair<Long, Int> {
        var result = 0L
        var shift = 0
        var index = offset
        while (index < bytes.size) {
            val current = bytes[index].toInt() and 0xff
            result = result or (((current and 0x7f).toLong()) shl shift)
            index++
            if (current and 0x80 == 0) {
                return result to index
            }
            shift += 7
            require(shift < 64) { "Varint is too large" }
        }
        error("Unexpected end of CID bytes")
    }
}
