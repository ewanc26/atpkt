package uk.ewancroft.atpkt.crypto

import java.security.*
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
}
