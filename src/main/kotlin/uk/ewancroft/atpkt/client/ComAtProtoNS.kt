package uk.ewancroft.atpkt.client

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.agent.Agent
import uk.ewancroft.atpkt.core.AtProtoClient
import uk.ewancroft.atpkt.xrpc.Xrpc
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private const val DEFAULT_PDS_URL = "https://bsky.social"

private fun encodeQuery(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8)

private fun buildQueryString(vararg params: Pair<String, String?>): String =
    params.mapNotNull { (key, value) -> value?.let { "${encodeQuery(key)}=${encodeQuery(it)}" } }
        .joinToString("&")

private suspend fun Agent.xrpcText(
    method: String,
    endpoint: String,
    pdsUrl: String,
    accessJwt: String? = null,
    body: String? = null
): String = sessionManager.client.xrpcRequest(
    method = method,
    endpoint = endpoint,
    accessJwt = accessJwt,
    pdsUrl = pdsUrl,
    body = body
).getOrThrow()

// ── com.atproto namespace ──────────────────────────

/**
 * Namespaced access for com.atproto.* XRPC endpoints.
 * Provides thin wrappers around XRPC calls to the relevant PDS.
 */
class ComAtProtoNS(private val agent: Agent) {
    val repo = ComAtProtoRepoNS(agent)
    val server = ComAtProtoServerNS(agent)
    val identity = ComAtProtoIdentityNS(agent)
    val sync = ComAtProtoSyncNS(agent)
}

@Serializable
data class CreateRecordRequest(
    val repo: String,
    val collection: String,
    val record: JsonElement,
    val rkey: String? = null,
    val validate: Boolean? = null
)

@Serializable
data class PutRecordRequest(
    val repo: String,
    val collection: String,
    val rkey: String,
    val record: JsonElement,
    val swapRecord: String? = null,
    val swapCommit: String? = null,
    val validate: Boolean? = null
)

@Serializable
data class DeleteRecordRequest(
    val repo: String,
    val collection: String,
    val rkey: String,
    val swapRecord: String? = null,
    val swapCommit: String? = null
)

@Serializable
data class CreateSessionRequest(
    val identifier: String,
    val password: String,
    val authFactorToken: String? = null
)

@Serializable
data class SessionResponse(
    val did: String,
    val handle: String,
    val email: String? = null,
    val emailConfirmed: Boolean? = null,
    val active: Boolean? = null,
    val accessJwt: String? = null,
    val refreshJwt: String? = null
)

@Serializable
data class DescribeServerResponse(
    val availableUserDomains: List<String>? = null,
    val inviteCodeRequired: Boolean? = null,
    val phoneVerificationRequired: Boolean? = null,
    val contact: JsonElement? = null,
    val links: JsonElement? = null,
    val did: String? = null
)

@Serializable
data class ResolveHandleResponse(
    val did: String
)

@Serializable
data class RecordView(
    val uri: String,
    val cid: String,
    val value: JsonElement
)

@Serializable
data class GetRecordResponse(
    val uri: String,
    val cid: String? = null,
    val value: JsonElement
)

@Serializable
data class ListRecordsResponse(
    val records: List<RecordView>,
    val cursor: String? = null
)

@Serializable
data class CreateRecordResponse(
    val uri: String,
    val cid: String
)

class ComAtProtoRepoNS(private val agent: Agent) {
    suspend fun createRecord(
        repo: String,
        collection: String,
        record: JsonElement,
        rkey: String? = null,
        validate: Boolean = true,
        accessJwt: String? = null
    ): Result<String> = runCatching {
        val pdsUrl = agent.resolvePds(repo).getOrThrow()
        val request = CreateRecordRequest(
            repo = repo,
            collection = collection,
            record = record,
            rkey = rkey,
            validate = validate
        )
        agent.xrpcText(
            method = "POST",
            endpoint = "com.atproto.repo.createRecord",
            pdsUrl = pdsUrl,
            accessJwt = accessJwt,
            body = Xrpc.json.encodeToString(CreateRecordRequest.serializer(), request)
        )
    }

    suspend fun getRecord(
        repo: String,
        collection: String,
        rkey: String,
        cid: String? = null,
        accessJwt: String? = null
    ): Result<GetRecordResponse> = runCatching {
        val pdsUrl = agent.resolvePds(repo).getOrThrow()
        val query = buildQueryString(
            "repo" to repo,
            "collection" to collection,
            "rkey" to rkey,
            "cid" to cid
        )
        val response = agent.xrpcText(
            method = "GET",
            endpoint = "com.atproto.repo.getRecord?$query",
            pdsUrl = pdsUrl,
            accessJwt = accessJwt
        )
        Xrpc.json.decodeFromString<GetRecordResponse>(response)
    }

    suspend fun listRecords(
        repo: String,
        collection: String,
        limit: Int = 50,
        cursor: String? = null,
        reverse: Boolean? = null,
        accessJwt: String? = null
    ): Result<ListRecordsResponse> = runCatching {
        val pdsUrl = agent.resolvePds(repo).getOrThrow()
        val query = buildQueryString(
            "repo" to repo,
            "collection" to collection,
            "limit" to limit.toString(),
            "cursor" to cursor,
            "reverse" to reverse?.toString()
        )
        val response = agent.xrpcText(
            method = "GET",
            endpoint = "com.atproto.repo.listRecords?$query",
            pdsUrl = pdsUrl,
            accessJwt = accessJwt
        )
        Xrpc.json.decodeFromString<ListRecordsResponse>(response)
    }

    suspend fun putRecord(
        repo: String,
        collection: String,
        record: JsonElement,
        rkey: String,
        swapRecord: String? = null,
        swapCommit: String? = null,
        validate: Boolean = true,
        accessJwt: String? = null
    ): Result<String> = runCatching {
        val pdsUrl = agent.resolvePds(repo).getOrThrow()
        val request = PutRecordRequest(
            repo = repo,
            collection = collection,
            rkey = rkey,
            record = record,
            swapRecord = swapRecord,
            swapCommit = swapCommit,
            validate = validate
        )
        agent.xrpcText(
            method = "POST",
            endpoint = "com.atproto.repo.putRecord",
            pdsUrl = pdsUrl,
            accessJwt = accessJwt,
            body = Xrpc.json.encodeToString(PutRecordRequest.serializer(), request)
        )
    }

    suspend fun deleteRecord(
        repo: String,
        collection: String,
        rkey: String,
        swapRecord: String? = null,
        swapCommit: String? = null,
        accessJwt: String? = null
    ): Result<String> = runCatching {
        val pdsUrl = agent.resolvePds(repo).getOrThrow()
        val request = DeleteRecordRequest(
            repo = repo,
            collection = collection,
            rkey = rkey,
            swapRecord = swapRecord,
            swapCommit = swapCommit
        )
        agent.xrpcText(
            method = "POST",
            endpoint = "com.atproto.repo.deleteRecord",
            pdsUrl = pdsUrl,
            accessJwt = accessJwt,
            body = Xrpc.json.encodeToString(DeleteRecordRequest.serializer(), request)
        )
    }

    suspend fun getProfileRequest(actor: String): Result<String> = runCatching {
        val pdsUrl = DEFAULT_PDS_URL
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.actor.getProfile?actor=$actor",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }

    suspend fun getPostThreadRequest(uri: String): Result<String> = runCatching {
        val pdsUrl = DEFAULT_PDS_URL
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.feed.getPostThread?uri=$uri",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }

    suspend fun getTimelineRequest(params: String): Result<String> = runCatching {
        val pdsUrl = DEFAULT_PDS_URL
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.feed.getTimeline?$params",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }

    suspend fun getFeedRequest(params: String): Result<String> = runCatching {
        val pdsUrl = DEFAULT_PDS_URL
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.feed.getFeed?$params",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }

    suspend fun listNotificationsRequest(params: String): Result<String> = runCatching {
        val pdsUrl = DEFAULT_PDS_URL
        agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "app.bsky.notification.listNotifications?$params",
            pdsUrl = pdsUrl
        ).getOrThrow()
    }
}

class ComAtProtoServerNS(private val agent: Agent) {
    suspend fun createSession(
        identifier: String,
        password: String,
        pdsUrl: String = DEFAULT_PDS_URL,
        authFactorToken: String? = null
    ): Result<AtProtoClient.CreateSessionResponse> = runCatching {
        val request = CreateSessionRequest(
            identifier = identifier,
            password = password,
            authFactorToken = authFactorToken
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "POST",
            endpoint = "com.atproto.server.createSession",
            pdsUrl = pdsUrl,
            body = Xrpc.json.encodeToString(CreateSessionRequest.serializer(), request)
        ).getOrThrow()
        Xrpc.json.decodeFromString<AtProtoClient.CreateSessionResponse>(response)
    }

    suspend fun getSession(
        accessJwt: String,
        pdsUrl: String = DEFAULT_PDS_URL
    ): Result<SessionResponse> = runCatching {
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "com.atproto.server.getSession",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<SessionResponse>(response)
    }

    suspend fun describeServer(
        pdsUrl: String = DEFAULT_PDS_URL
    ): Result<DescribeServerResponse> = runCatching {
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "com.atproto.server.describeServer",
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<DescribeServerResponse>(response)
    }
}

class ComAtProtoIdentityNS(private val agent: Agent) {
    suspend fun resolveHandle(
        handle: String,
        pdsUrl: String = DEFAULT_PDS_URL
    ): Result<ResolveHandleResponse> = runCatching {
        val query = buildQueryString("handle" to handle)
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "com.atproto.identity.resolveHandle?$query",
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<ResolveHandleResponse>(response)
    }
}

class ComAtProtoSyncNS(private val agent: Agent) {
    suspend fun getRepo(
        did: String,
        since: String? = null,
        accessJwt: String? = null
    ): Result<ByteArray> = runCatching {
        val pdsUrl = agent.resolvePds(did).getOrThrow()
        val query = buildQueryString(
            "did" to did,
            "since" to since
        )
        val response = AtpHttpClient.client.get("$pdsUrl/xrpc/com.atproto.sync.getRepo?$query") {
            accessJwt?.let { header(HttpHeaders.Authorization, "Bearer $it") }
            header(HttpHeaders.Accept, "application/vnd.ipld.car")
        }
        if (!response.status.isSuccess()) {
            val bodyText = response.bodyAsText()
            val error = try {
                Xrpc.json.decodeFromString<uk.ewancroft.atpkt.xrpc.Xrpc.XrpcError>(bodyText)
            } catch (_: Exception) {
                uk.ewancroft.atpkt.xrpc.Xrpc.XrpcError("UnknownError", bodyText)
            }
            throw Exception("XRPC Error: ${error.error} - ${error.message}")
        }
        response.body<ByteArray>()
    }
}
