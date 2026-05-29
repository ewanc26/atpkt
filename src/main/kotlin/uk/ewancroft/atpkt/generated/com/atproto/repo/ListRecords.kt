package uk.ewancroft.atpkt.generated.com.atproto.repo

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class ListRecordsOutput(
  public val unused: String? = null,
)

@Serializable
public data class Record(
  public val uri: String? = null,
  public val cid: String? = null,
  public val `value`: JsonElement? = null,
)
