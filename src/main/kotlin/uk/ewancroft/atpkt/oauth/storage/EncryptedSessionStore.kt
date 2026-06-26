package uk.ewancroft.atpkt.oauth.storage

import java.nio.charset.StandardCharsets.UTF_8
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Encrypts session payloads before delegating to another store.
 */
class EncryptedSessionStore(
    private val delegate: SessionStore,
    private val key: SecretKey
) : ListableSessionStore {
    private val secureRandom = SecureRandom()

    override suspend fun get(key: String): String? {
        val encrypted = delegate.get(key) ?: return null
        return decrypt(encrypted)
    }

    override suspend fun set(key: String, value: String) {
        delegate.set(key, encrypt(value))
    }

    override suspend fun del(key: String) {
        delegate.del(key)
    }

    override suspend fun list(): List<String> = (delegate as? ListableSessionStore)?.list().orEmpty()

    private fun encrypt(value: String): String {
        val iv = ByteArray(IV_LENGTH_BYTES).also(secureRandom::nextBytes)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key, GCM_PARAMETER_SPEC(iv))
        val ciphertext = cipher.doFinal(value.toByteArray(UTF_8))
        return Base64.getEncoder().encodeToString(iv + ciphertext)
    }

    private fun decrypt(value: String): String {
        val raw = Base64.getDecoder().decode(value)
        require(raw.size > IV_LENGTH_BYTES) { "Encrypted payload is too short" }

        val iv = raw.copyOfRange(0, IV_LENGTH_BYTES)
        val ciphertext = raw.copyOfRange(IV_LENGTH_BYTES, raw.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, GCM_PARAMETER_SPEC(iv))
        return String(cipher.doFinal(ciphertext), UTF_8)
    }

    private fun GCM_PARAMETER_SPEC(iv: ByteArray): GCMParameterSpec = GCMParameterSpec(TAG_LENGTH_BITS, iv)

    private companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val IV_LENGTH_BYTES = 12
        private const val TAG_LENGTH_BITS = 128
    }
}
