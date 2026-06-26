package uk.ewancroft.atpkt.generated.com.atproto.repo

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class CreateRecordInput(
  public val repo: String,
  public val collection: String,
  public val rkey: String? = null,
  public val validate: Boolean? = null,
  public val record: JsonElement,
  public val swapCommit: String? = null,
)

@Serializable
public data class CreateRecordOutput(
  public val uri: String,
  public val cid: String,
  public val commit: CommitMeta? = null,
  public val validationStatus: String? = null,
)
