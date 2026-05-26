package uk.ewancroft.atpmc.core

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.crypto.SecretKey
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Manages AT Protocol authentication sessions.
 * Handles token storage, automatic refresh, and session lifecycle.
 * 
 * NOTE: This is the core library implementation. Actual storage encryption 
 * logic should be provided or handled by the implementation using an 
 * `IEncryptionProvider` interface.
 */
class AtProtoSessionManager(
    private val storageFile: Path,
    val client: AtProtoClient,
    private val encryptionProvider: IEncryptionProvider
) {
    private val logger = LoggerFactory.getLogger("atpmc-session")
    private val sessions = ConcurrentHashMap<UUID, PlayerSession>()
    private val fileLock = ReentrantReadWriteLock()
    
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    interface IEncryptionProvider {
        fun encrypt(plaintext: String): String
        fun decrypt(ciphertext: String): String
    }

    @Serializable
    data class PlayerSession(
        val did: String,
        val handle: String,
        val pdsUrl: String,
        val accessJwt: String,
        val refreshJwt: String,
        val createdAt: Long = System.currentTimeMillis(),
        val lastRefreshed: Long = System.currentTimeMillis(),
        val authType: String = "app_password",
    )

    @Serializable
    private data class SessionStorage(
        val version: Int = 2,
        val sessions: Map<String, PlayerSession>
    )

    init {
        load()
        logger.info("Session manager initialized")
    }

    fun storeSession(
        uuid: UUID,
        did: String,
        handle: String,
        pdsUrl: String,
        accessJwt: String,
        refreshJwt: String,
        authType: String = "app_password",
    ) {
        val session = PlayerSession(
            did = did,
            handle = handle,
            pdsUrl = pdsUrl,
            accessJwt = accessJwt,
            refreshJwt = refreshJwt,
            createdAt = System.currentTimeMillis(),
            lastRefreshed = System.currentTimeMillis(),
            authType = authType,
        )
        
        sessions[uuid] = session
        save()
    }

    suspend fun getSession(uuid: UUID): Result<PlayerSession> = runCatching {
        val session = sessions[uuid] ?: throw Exception("No session found")
        
        val hoursSinceRefresh = (System.currentTimeMillis() - session.lastRefreshed) / (1000.0 * 60 * 60)
        
        if (hoursSinceRefresh >= 1.5) {
            return refreshSession(uuid)
        }
        
        session
    }

    suspend fun refreshSession(uuid: UUID): Result<PlayerSession> = runCatching {
        val oldSession = sessions[uuid] ?: throw Exception("No session")
        
        val refreshResponse = client.xrpcRequest(
            method = "POST",
            endpoint = "com.atproto.server.refreshSession",
            pdsUrl = oldSession.pdsUrl,
            body = """{"refreshJwt": "${oldSession.refreshJwt}"}"""
        ).getOrThrow()
        
        val tokens = json.decodeFromString<AtProtoClient.CreateSessionResponse>(refreshResponse)
        
        val newSession = oldSession.copy(
            accessJwt = tokens.accessJwt,
            refreshJwt = tokens.refreshJwt,
            lastRefreshed = System.currentTimeMillis()
        )
        
        sessions[uuid] = newSession
        save()
        newSession
    }

    private fun load() = fileLock.read {
        try {
            if (Files.exists(storageFile)) {
                val encrypted = Files.readString(storageFile)
                val plaintext = encryptionProvider.decrypt(encrypted)
                val storage = json.decodeFromString<SessionStorage>(plaintext)
                
                storage.sessions.forEach { (uuid, session) ->
                    sessions[UUID.fromString(uuid)] = session
                }
            }
        } catch (e: Exception) {
            logger.error("Failed to load sessions", e)
        }
    }

    private fun save() = fileLock.write {
        try {
            val storage = SessionStorage(
                version = 2,
                sessions = sessions.mapKeys { it.key.toString() }
            )
            
            val plaintext = json.encodeToString(storage)
            val encrypted = encryptionProvider.encrypt(plaintext)
            
            Files.writeString(storageFile, encrypted, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
        } catch (e: Exception) {
            logger.error("Failed to save sessions", e)
        }
    }
}
