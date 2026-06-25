package uk.ewancroft.atpkt.core

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import org.slf4j.LoggerFactory
import java.util.UUID

// ── Record management ──────────────────────────────

/**
 * Comprehensive record management for AT Protocol repositories.
 * Provides type-safe CRUD operations for AT Protocol records.
 */
class RecordManager(
    private val sessionManager: AtProtoSessionManager
) {
    private val logger = LoggerFactory.getLogger("atpmc-record-manager")
    private val json = Json {
        prettyPrint = false
        ignoreUnknownKeys = true
    }

    suspend fun createRecord(
        playerUuid: UUID,
        collection: String,
        record: JsonElement,
        validate: Boolean = true
    ): Result<StrongRef> = runCatching {
        logger.info("Creating record in collection: $collection")
        
        val session = sessionManager.getSession(playerUuid).getOrThrow()
        
        val request = CreateRecordRequest(
            repo = session.did,
            collection = collection,
            record = record,
            validate = validate
        )
        
        val responseBody = sessionManager.client.xrpcRequest(
            method = "POST",
            endpoint = "com.atproto.repo.createRecord",
            accessJwt = session.accessJwt,
            pdsUrl = session.pdsUrl,
            body = json.encodeToString(CreateRecordRequest.serializer(), request)
        ).getOrThrow()
        
        val response = json.decodeFromString<CreateRecordResponse>(responseBody)
        StrongRef(uri = response.uri, cid = response.cid)
    }

    suspend fun getRecord(
        playerUuid: UUID,
        collection: String,
        rkey: String,
        cid: String? = null
    ): Result<RecordData> = runCatching {
        val session = sessionManager.getSession(playerUuid).getOrThrow()
        
        val params = buildString {
            append("repo=${session.did}")
            append("&collection=$collection")
            append("&rkey=$rkey")
            cid?.let { append("&cid=$it") }
        }
        
        val responseBody = sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "com.atproto.repo.getRecord?$params",
            accessJwt = session.accessJwt,
            pdsUrl = session.pdsUrl
        ).getOrThrow()
        
        val response = json.decodeFromString<GetRecordResponse>(responseBody)
        RecordData(uri = response.uri, value = response.value, cid = response.cid)
    }

    fun generateTID(): String = Tid.generate()

    @Serializable data class CreateRecordRequest(val repo: String, val collection: String, val record: JsonElement, val rkey: String? = null, val validate: Boolean? = null)
    @Serializable data class CreateRecordResponse(val uri: String, val cid: String)
    @Serializable data class GetRecordResponse(val uri: String, val cid: String?, val value: JsonElement)
    data class StrongRef(val uri: String, val cid: String)
    data class RecordData(val uri: String, val value: JsonElement, val cid: String?)
}
