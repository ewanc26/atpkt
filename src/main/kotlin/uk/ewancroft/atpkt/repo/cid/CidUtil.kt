package uk.ewancroft.atpkt.repo.cid

import java.security.MessageDigest

// ── CID computation ────────────────────────────────

/**
 * CID (Content Identifier) utility for hashing objects to verify their contents.
 * AT Protocol uses CIDv1. This implementation aligns with the basic structure:
 * CIDv1 = [version] + [multicodec] + [multihash]
 *
 * Spec: https://atproto.com/specs/cid
 */
object CidUtil {
    
    /**
     * Computes a CIDv1-like identifier for the given data.
     * Uses SHA-256 (0x12), DAG-CBOR (0x71), and CIDv1 (0x01).
     */
    fun computeCid(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(data)
        
        // CIDv1 prefix: 
        // 0x01 (version)
        // 0x71 (dag-cbor multicodec)
        // 0x12 (sha2-256 multihash)
        // 0x20 (32 bytes length)
        val prefix = byteArrayOf(0x01.toByte(), 0x71.toByte(), 0x12.toByte(), 0x20.toByte())
        val cidBytes = prefix + digest
        
        // Base32 encode with 'b' prefix (multibase)
        return "b" + base32Encode(cidBytes).lowercase().replace("=", "")
    }

    private fun base32Encode(data: ByteArray): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"
        var i = 0
        var index = 0
        var digit = 0
        var currByte: Int
        var nextByte: Int
        val base32 = StringBuilder((data.size + 7) * 8 / 5)

        while (i < data.size) {
            currByte = data[i].toInt() and 0xFF
            if (index > 3) {
                if (i + 1 < data.size) {
                    nextByte = data[i + 1].toInt() and 0xFF
                } else {
                    nextByte = 0
                }
                digit = currByte and (0xFF shr index)
                index = (index + 5) % 8
                digit = digit shl index
                digit = digit or (nextByte shr (8 - index))
                i++
            } else {
                digit = (currByte shr (8 - (index + 5))) and 0x1F
                index = (index + 5) % 8
                if (index == 0) i++
            }
            base32.append(alphabet[digit])
        }
        return base32.toString()
    }
}
