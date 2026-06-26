package uk.ewancroft.atpkt.client

import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.agent.Agent
import uk.ewancroft.atpkt.xrpc.Xrpc
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private fun encodeQuery(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8)

private fun buildQueryString(vararg params: Pair<String, String?>): String =
    params.mapNotNull { (key, value) -> value?.let { "${encodeQuery(key)}=${encodeQuery(it)}" } }
        .joinToString("&")

private const val DEFAULT_PDS_URL = "https://bsky.social"

class ToolsOzoneNS(private val agent: Agent) {
    val communication = ToolsOzoneCommunicationNS(agent)
    val hosting = ToolsOzoneHostingNS(agent)
    val moderation = ToolsOzoneModerationNS(agent)
    val queue = ToolsOzoneQueueNS(agent)
    val report = ToolsOzoneReportNS(agent)
    val safelink = ToolsOzoneSafelinkNS(agent)
    val server = ToolsOzoneServerNS(agent)
    val set = ToolsOzoneSetNS(agent)
    val setting = ToolsOzoneSettingNS(agent)
    val signature = ToolsOzoneSignatureNS(agent)
    val team = ToolsOzoneTeamNS(agent)
    val verification = ToolsOzoneVerificationNS(agent)
}

class ToolsOzoneCommunicationNS(private val agent: Agent) {
    suspend fun createTemplate(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun deleteTemplate(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listTemplates(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun updateTemplate(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneHostingNS(private val agent: Agent) {
    suspend fun getAccountHistory(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneModerationNS(private val agent: Agent) {
    suspend fun cancelScheduledActions(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun emitEvent(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getAccountTimeline(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getEvent(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getRecord(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getRecords(): Result<String> = runCatching { TODO("not yet implemented") }

    suspend fun getRepo(
        did: String,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val params = buildQueryString("did" to did)
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "tools.ozone.moderation.getRepo?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }

    suspend fun getReporterStats(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getRepos(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getSubjects(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listScheduledActions(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun queryEvents(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun queryStatuses(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun scheduleAction(): Result<String> = runCatching { TODO("not yet implemented") }

    suspend fun searchRepos(
        term: String,
        limit: Int = 25,
        cursor: String? = null,
        pdsUrl: String = DEFAULT_PDS_URL,
        accessJwt: String? = null
    ): Result<JsonElement> = runCatching {
        val params = buildQueryString(
            "term" to term,
            "limit" to limit.toString(),
            "cursor" to cursor
        )
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "tools.ozone.moderation.searchRepos?$params",
            accessJwt = accessJwt,
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }
}

class ToolsOzoneQueueNS(private val agent: Agent) {
    suspend fun assignModerator(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun createQueue(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun deleteQueue(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getAssignments(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listQueues(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun routeReports(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun unassignModerator(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun updateQueue(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneReportNS(private val agent: Agent) {
    suspend fun assignModerator(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun createActivity(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getAssignments(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getHistoricalStats(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getLatestReport(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getLiveStats(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getReport(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listActivities(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun queryReports(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun reassignQueue(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun refreshStats(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun unassignModerator(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneSafelinkNS(private val agent: Agent) {
    suspend fun addRule(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun queryEvents(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun queryRules(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun removeRule(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun updateRule(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneServerNS(private val agent: Agent) {
    suspend fun getConfig(
        pdsUrl: String = DEFAULT_PDS_URL
    ): Result<JsonElement> = runCatching {
        val response = agent.sessionManager.client.xrpcRequest(
            method = "GET",
            endpoint = "tools.ozone.server.getConfig",
            pdsUrl = pdsUrl
        ).getOrThrow()
        Xrpc.json.decodeFromString<JsonElement>(response)
    }
}

class ToolsOzoneSetNS(private val agent: Agent) {
    suspend fun addValues(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun deleteSet(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun deleteValues(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun getValues(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun querySets(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun upsertSet(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneSettingNS(private val agent: Agent) {
    suspend fun listOptions(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun removeOptions(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun upsertOption(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneSignatureNS(private val agent: Agent) {
    suspend fun findCorrelation(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun findRelatedAccounts(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun searchAccounts(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneTeamNS(private val agent: Agent) {
    suspend fun addMember(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun deleteMember(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listMembers(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun updateMember(): Result<String> = runCatching { TODO("not yet implemented") }
}

class ToolsOzoneVerificationNS(private val agent: Agent) {
    suspend fun grantVerifications(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun listVerifications(): Result<String> = runCatching { TODO("not yet implemented") }
    suspend fun revokeVerifications(): Result<String> = runCatching { TODO("not yet implemented") }
}
