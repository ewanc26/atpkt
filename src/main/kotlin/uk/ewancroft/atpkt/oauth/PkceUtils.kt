package uk.ewancroft.atpkt.oauth

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

// ── PKCE utilities ─────────────────────────────────

/**
 * Utility for Proof Key for Code Exchange (PKCE).
 * Spec: RFC 7636
 *
 * Generates a code_verifier and its SHA-256 code_challenge for the OAuth
 * authorization code flow. Prevents authorisation code interception attacks.
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
