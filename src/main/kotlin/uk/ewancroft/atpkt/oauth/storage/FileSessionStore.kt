package uk.ewancroft.atpkt.oauth.storage

import java.nio.charset.StandardCharsets
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import kotlin.io.use

/**
 * File-backed session store that persists each session as a JSON file.
 */
class FileSessionStore(
    private val directory: Path
) : ListableSessionStore {
    init {
        Files.createDirectories(directory)
    }

    override suspend fun get(key: String): String? {
        val path = pathFor(key)
        return try {
            if (Files.exists(path)) {
                Files.readString(path, StandardCharsets.UTF_8)
            } else {
                null
            }
        } catch (_: java.nio.file.NoSuchFileException) {
            null
        }
    }

    override suspend fun set(key: String, value: String) {
        Files.createDirectories(directory)

        val path = pathFor(key)
        val tempFile = Files.createTempFile(directory, "${path.fileName}.", ".tmp")
        try {
            Files.writeString(tempFile, value, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)
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

    override suspend fun list(): List<String> {
        if (!Files.exists(directory)) return emptyList()

        return Files.list(directory).use { stream ->
            stream.iterator()
                .asSequence()
                .filter { Files.isRegularFile(it) && it.fileName.toString().endsWith(".json") }
                .map { it.fileName.toString().removeSuffix(".json") }
                .sorted()
                .toList()
        }
    }

    private fun pathFor(key: String): Path = directory.resolve("$key.json")
}
