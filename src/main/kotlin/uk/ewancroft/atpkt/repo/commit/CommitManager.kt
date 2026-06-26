package uk.ewancroft.atpkt.repo.commit

import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.crypto.CryptoUtil
import uk.ewancroft.atpkt.repo.cbor.CborBytes
import uk.ewancroft.atpkt.repo.cbor.CborEncoder
import uk.ewancroft.atpkt.repo.cbor.CborInt
import uk.ewancroft.atpkt.repo.cbor.CborMap
import uk.ewancroft.atpkt.repo.cbor.CborNull
import uk.ewancroft.atpkt.repo.cbor.CborString
import uk.ewancroft.atpkt.repo.cid.CidUtil
import java.security.PrivateKey
import java.security.PublicKey

/**
 * Represents an unsigned AT Protocol commit object.
 */
@Serializable
data class UnsignedCommit(
    val did: String,
    val version: Int = 3,
    val rev: String,
    val prev: String?,
    val data: String
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Commit) return false
        return did == other.did &&
            version == other.version &&
            rev == other.rev &&
            prev == other.prev &&
            data == other.data &&
            sig.contentEquals(other.sig)
    }

    override fun hashCode(): Int {
        var result = did.hashCode()
        result = 31 * result + version
        result = 31 * result + rev.hashCode()
        result = 31 * result + (prev?.hashCode() ?: 0)
        result = 31 * result + data.hashCode()
        result = 31 * result + sig.contentHashCode()
        return result
    }

    fun cid(): String = CommitManager.cid(this)
}

/**
 * Factory for creating and verifying authenticated repository commits.
 */
object CommitManager {
    private val encoder = CborEncoder()

    fun sign(unsigned: UnsignedCommit, privateKey: PrivateKey): Commit {
        val encoded = encodeUnsignedCommit(unsigned)
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
        return CryptoUtil.verifyCommit(encodeUnsignedCommit(unsigned), commit.sig, publicKey)
    }

    fun cid(commit: Commit): String = CidUtil.computeCid(encodeCommit(commit))

    fun startSession(signingKey: PrivateKey, verifyingKey: PublicKey): Session = Session(signingKey, verifyingKey)

    private fun encodeUnsignedCommit(unsigned: UnsignedCommit): ByteArray {
        val fields = linkedMapOf<String, uk.ewancroft.atpkt.repo.cbor.DagCborValue>(
            "did" to CborString(unsigned.did),
            "version" to CborInt(unsigned.version.toLong()),
            "rev" to CborString(unsigned.rev),
            "prev" to (unsigned.prev?.let { encoder.cidLink(it) } ?: CborNull),
            "data" to encoder.cidLink(unsigned.data)
        )
        return encoder.encode(CborMap(fields))
    }

    private fun encodeCommit(commit: Commit): ByteArray {
        val fields = linkedMapOf<String, uk.ewancroft.atpkt.repo.cbor.DagCborValue>(
            "did" to CborString(commit.did),
            "version" to CborInt(commit.version.toLong()),
            "rev" to CborString(commit.rev),
            "prev" to (commit.prev?.let { encoder.cidLink(it) } ?: CborNull),
            "data" to encoder.cidLink(commit.data),
            "sig" to CborBytes(commit.sig)
        )
        return encoder.encode(CborMap(fields))
    }

    /**
     * Stateful helper that chains commits via the prev pointer.
     */
    class Session internal constructor(
        private val signingKey: PrivateKey,
        private val verifyingKey: PublicKey
    ) {
        private val history = mutableListOf<Commit>()

        fun createCommit(did: String, rev: String, data: String): Commit {
            val unsigned = UnsignedCommit(
                did = did,
                rev = rev,
                prev = history.lastOrNull()?.cid(),
                data = data
            )
            val commit = sign(unsigned, signingKey)
            history += commit
            return commit
        }

        fun verify(commit: Commit): Boolean = CommitManager.verify(commit, verifyingKey)

        fun history(): List<Commit> = history.toList()

        fun latest(): Commit? = history.lastOrNull()
    }
}
