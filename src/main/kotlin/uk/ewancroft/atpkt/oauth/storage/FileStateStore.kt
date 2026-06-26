package uk.ewancroft.atpkt.oauth.storage

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import kotlin.io.use
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

/**
 * File-backed state store with expiry metadata stored alongside the value.
 */
class FileStateStore(
    private val directory: Path,
    private val ttl: Duration = 10.minutes
) : StateStore {
    private val json = Json { ignoreUnknownKeys = true }

    @Serializable
    private data class StoredState(
        val value: String,
        val expiresAtMillis: Long
    )

    init {
        Files.createDirectories(directory)
    }

    override suspend fun get(key: String): String? {
        val path = pathFor(key)
        if (!Files.exists(path)) return null

        val stored = runCatching {
            json.decodeFromString<StoredState>(Files.readString(path, StandardCharsets.UTF_8))
        }.getOrNull() ?: return null

        if (stored.isExpired()) {
            Files.deleteIfExists(path)
            return null
        }

        return stored.value
    }

    override suspend fun set(key: String, value: String) {
        Files.createDirectories(directory)

        val path = pathFor(key)
        val tempFile = Files.createTempFile(directory, "${path.fileName}.", ".tmp")
        try {
            val payload = json.encodeToString(
                StoredState(
                    value = value,
                    expiresAtMillis = System.currentTimeMillis() + ttl.inWholeMilliseconds
                )
            )
            Files.writeString(tempFile, payload, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)
            try {
                Files.move(
                    tempFile,
                    path,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
                )
            } catch (_: AtomicMoveNotSupportedException) {
                Files.move(tempFile, path, StandardCopyOption.REPLACE_EXISTING)
            }
        } finally {
            Files.deleteIfExists(tempFile)
        }
    }

    override suspend fun del(key: String) {
        Files.deleteIfExists(pathFor(key))
    }

    private fun pathFor(key: String): Path = directory.resolve("$key.json")

    private fun StoredState.isExpired(): Boolean = expiresAtMillis <= System.currentTimeMillis()
}
