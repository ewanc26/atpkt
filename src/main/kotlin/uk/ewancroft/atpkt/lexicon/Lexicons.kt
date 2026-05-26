package uk.ewancroft.atpkt.lexicon

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Base record interface for AT Protocol lexicons.
 * All AT Protocol records should extend this or use it as a base.
 */
interface AtProtoRecord {
    val type: String
}

/**
 * Common schema definitions used across AT Protocol services.
 */
object Lexicons {
    const val COM_ATPROTO_REPO_CREATE_RECORD = "com.atproto.repo.createRecord"
    const val COM_ATPROTO_REPO_PUT_RECORD = "com.atproto.repo.putRecord"
    const val COM_ATPROTO_REPO_GET_RECORD = "com.atproto.repo.getRecord"
    const val COM_ATPROTO_REPO_LIST_RECORDS = "com.atproto.repo.listRecords"
    const val COM_ATPROTO_REPO_DELETE_RECORD = "com.atproto.repo.deleteRecord"
    const val COM_ATPROTO_SERVER_CREATE_SESSION = "com.atproto.server.createSession"
    const val COM_ATPROTO_SERVER_REFRESH_SESSION = "com.atproto.server.refreshSession"
}
