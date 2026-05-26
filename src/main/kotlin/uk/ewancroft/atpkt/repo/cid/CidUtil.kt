package uk.ewancroft.atpkt.repo.cid

import java.security.MessageDigest
import java.util.Base64

/**
 * CID (Content Identifier) utility for hashing objects to verify their contents.
 * AT Protocol uses CIDv1, but we start with a robust SHA-256 base for node verification.
 */
object CidUtil {
    
    /**
     * Computes a simple SHA-256 fingerprint of the data.
     * In a production ATProtocol SDK, this would be replaced with actual CIDv1 multi-hash logic.
     */
    fun computeCid(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(data)
        // Encode as a standard string identifier; production ATProto uses multibase
        return "b" + Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
    }
}
