package uk.ewancroft.atpkt.oauth

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

/**
 * Utility for Proof Key for Code Exchange (PKCE).
 */
object PkceUtils {
    private val secureRandom = SecureRandom()
    private val base64UrlEncoder = Base64.getUrlEncoder().withoutPadding()

    fun generateCodeVerifier(): String {
        val bytes = ByteArray(32)
        secureRandom.nextBytes(bytes)
        return base64UrlEncoder.encodeToString(bytes)
    }

    fun generateCodeChallenge(codeVerifier: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(codeVerifier.toByteArray(Charsets.US_ASCII))
        return base64UrlEncoder.encodeToString(hash)
    }

    fun getChallengeMethod(): String = "S256"
}
