package uk.ewancroft.atpkt.oauth.dpop

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uk.ewancroft.atpkt.crypto.CryptoUtil
import java.security.PrivateKey
import java.time.Instant
import java.util.Base64
import java.util.UUID

/**
 * Utility for generating DPoP (Demonstrating Proof-of-Possession) proofs.
 * Mirrors the cryptographic requirements for AT Protocol DPoP.
 */
object DpopProof {
    private val json = Json { encodeDefaults = true }

    @Serializable
    data class DpopHeader(
        val typ: String = "dpop+jwt",
        val alg: String = "ES256"
    )

    @Serializable
    data class DpopPayload(
        val jti: String = UUID.randomUUID().toString(),
        val htm: String, // HTTP Method (e.g., POST)
        val htu: String, // HTTP URL (e.g., https://pds.social/xrpc/...)
        val iat: Long = Instant.now().epochSecond
    )

    fun createProof(
        method: String,
        url: String,
        privateKey: PrivateKey
    ): String {
        val header = DpopHeader()
        val payload = DpopPayload(htm = method.uppercase(), htu = url)
        
        val encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(json.encodeToString(header).toByteArray())
        val encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(json.encodeToString(payload).toByteArray())
        
        val signingInput = "$encodedHeader.$encodedPayload"
        val signature = CryptoUtil.signCommit(signingInput.toByteArray(), privateKey)
        val encodedSignature = Base64.getUrlEncoder().withoutPadding().encodeToString(signature)
        
        return "$signingInput.$encodedSignature"
    }
}
