package uk.ewancroft.atpkt.crypto

import java.security.*
import java.security.interfaces.ECPublicKey
import java.util.Base64

/**
 * Utility for signing AT Protocol commits.
 * This implementation provides basic ECDSA P-256 signing as required by AT Protocol spec.
 */
object CryptoUtil {
    
    /**
     * Signs the commit data using the provided private key.
     * In a production environment, this would interface with a Keypair/KMS.
     */
    fun signCommit(data: ByteArray, privateKey: PrivateKey): ByteArray {
        val signer = Signature.getInstance("SHA256withECDSA")
        signer.initSign(privateKey)
        signer.update(data)
        return signer.sign()
    }

    /**
     * Verifies the signature of the commit data using the provided public key.
     */
    fun verifyCommit(data: ByteArray, signature: ByteArray, publicKey: PublicKey): Boolean {
        val verifier = Signature.getInstance("SHA256withECDSA")
        verifier.initVerify(publicKey)
        verifier.update(data)
        return verifier.verify(signature)
    }

    fun generateKeyPair(): KeyPair {
        val kpg = KeyPairGenerator.getInstance("EC")
        kpg.initialize(256)
        return kpg.generateKeyPair()
    }

    /**
     * Converts an EC Public Key to a JWK map for DPoP/OAuth.
     */
    fun getJwk(publicKey: PublicKey): Map<String, String> {
        val ecKey = publicKey as? ECPublicKey ?: throw IllegalArgumentException("Only EC keys are supported")
        val x = ecKey.w.affineX.toByteArray().normalize()
        val y = ecKey.w.affineY.toByteArray().normalize()

        return mapOf(
            "kty" to "EC",
            "crv" to "P-256",
            "x" to Base64.getUrlEncoder().withoutPadding().encodeToString(x),
            "y" to Base64.getUrlEncoder().withoutPadding().encodeToString(y)
        )
    }

    private fun ByteArray.normalize(): ByteArray {
        return if (size > 32) {
            copyOfRange(size - 32, size)
        } else if (size < 32) {
            ByteArray(32 - size) + this
        } else {
            this
        }
    }
}
