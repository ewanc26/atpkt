package uk.ewancroft.atpkt.repo.commit

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.crypto.CryptoUtil
import java.security.PrivateKey
import java.security.PublicKey

/**
 * Represents an unsigned AT Protocol commit object.
 * Mirrors the `UnsignedCommit` interface from ATProtocol.
 */
@Serializable
data class UnsignedCommit(
    val did: String,
    val version: Int = 3,
    val rev: String,
    val prev: String?, // CID
    val data: String   // CID
)

/**
 * Represents a signed AT Protocol commit.
 */
@Serializable
data class Commit(
    val did: String,
    val version: Int = 3,
    val rev: String,
    val prev: String?,
    val data: String,
    val sig: ByteArray
)

/**
 * Factory for creating and verifying authenticated repository commits.
 */
@OptIn(ExperimentalSerializationApi::class)
object CommitManager {
    
    fun sign(unsigned: UnsignedCommit, privateKey: PrivateKey): Commit {
        val encoded = kotlinx.serialization.cbor.Cbor.encodeToByteArray(UnsignedCommit.serializer(), unsigned)
        val sig = CryptoUtil.signCommit(encoded, privateKey)
        return Commit(
            did = unsigned.did,
            version = unsigned.version,
            rev = unsigned.rev,
            prev = unsigned.prev,
            data = unsigned.data,
            sig = sig
        )
    }

    fun verify(commit: Commit, publicKey: PublicKey): Boolean {
        val unsigned = UnsignedCommit(
            did = commit.did,
            version = commit.version,
            rev = commit.rev,
            prev = commit.prev,
            data = commit.data
        )
        val encoded = kotlinx.serialization.cbor.Cbor.encodeToByteArray(UnsignedCommit.serializer(), unsigned)
        return CryptoUtil.verifyCommit(encoded, commit.sig, publicKey)
    }
}
