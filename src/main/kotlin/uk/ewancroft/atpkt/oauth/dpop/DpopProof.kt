package uk.ewancroft.atpkt.oauth.dpop

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uk.ewancroft.atpkt.crypto.CryptoUtil
import java.security.PrivateKey
import java.security.PublicKey
import java.time.Instant
import java.util.Base64
import java.util.UUID

// ── DPoP proof generation ──────────────────────────

/**
 * Utility for generating DPoP (Demonstrating Proof-of-Possession) proofs.
 * Spec: RFC 9449
 *
 * Binds OAuth access tokens to the client's key pair, preventing
 * token theft by requiring proof of possession on every request.
 */
object DpopProof {
    private val json = Json { 
        encodeDefaults = true
        explicitNulls = false
    }

    @Serializable
    data class DpopHeader(
        val typ: String = "dpop+jwt",
        val alg: String = "ES256",
        val jwk: Map<String, String>
    )

    @Serializable
    data class DpopPayload(
        val jti: String = UUID.randomUUID().toString(),
        val htm: String, // HTTP Method (e.g., POST)
        val htu: String, // HTTP URL (e.g., https://pds.social/xrpc/...)
        val iat: Long = Instant.now().epochSecond,
        val nonce: String? = null,
        val ath: String? = null
    )

    /**
     * Creates a signed DPoP proof JWT.
     *
     * @param method The HTTP method (GET, POST, etc.)
     * @param url The target URL of the request
     * @param privateKey The DPoP private key
     * @param publicKey The DPoP public key
     * @param nonce Optional server-issued nonce (from DPoP-Nonce header)
     * @param accessToken Optional access token to include its hash (ath claim)
     * @return The signed DPoP proof JWT string
     */
    fun createProof(
        method: String,
        url: String,
        privateKey: PrivateKey,
        publicKey: PublicKey,
        nonce: String? = null,
        accessToken: String? = null
    ): String {
        val header = DpopHeader(jwk = CryptoUtil.getJwk(publicKey))
        val payload = DpopPayload(
            htm = method.uppercase(),
            htu = url,
            nonce = nonce,
            ath = accessToken?.let { sha256Base64Url(it) }
        )
        
        val encoder = Base64.getUrlEncoder().withoutPadding()
        val encodedHeader = encoder.encodeToString(json.encodeToString(header).toByteArray())
        val encodedPayload = encoder.encodeToString(json.encodeToString(payload).toByteArray())
        
        val signingInput = "$encodedHeader.$encodedPayload"
        val signature = CryptoUtil.signCommit(signingInput.toByteArray(), privateKey)
        val encodedSignature = encoder.encodeToString(signature)
        
        return "$signingInput.$encodedSignature"
    }

    /**
     * Computes the SHA-256 hash of the access token, base64url-encoded.
     * Used as the `ath` claim in DPoP proofs for resource server requests.
     */
    private fun sha256Base64Url(input: String): String {
        val digest = java.security.MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray(Charsets.US_ASCII))
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash)
    }
}
