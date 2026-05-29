package uk.ewancroft.atpkt.generated.app.bsky.video

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class JobStatus(
  public val jobId: String? = null,
  public val did: String? = null,
  public val state: String? = null,
  public val progress: Long? = null,
  public val blob: JsonElement? = null,
  public val error: String? = null,
  public val message: String? = null,
)
