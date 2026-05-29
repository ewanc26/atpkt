package uk.ewancroft.atpkt.generated.com.atproto.moderation

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class CreateReportInput(
  public val unused: String? = null,
)

@Serializable
public data class CreateReportOutput(
  public val unused: String? = null,
)

@Serializable
public data class ModTool(
  public val name: String? = null,
  public val meta: JsonElement? = null,
)
