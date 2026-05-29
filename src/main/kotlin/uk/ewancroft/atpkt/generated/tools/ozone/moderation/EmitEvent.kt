package uk.ewancroft.atpkt.generated.tools.ozone.moderation

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class EmitEventInput(
  public val unused: String? = null,
)

@Serializable
public data class EmitEventOutput(
  public val unused: String? = null,
)

@Serializable
public data class ReportAction(
  public val ids: List<Long?>? = null,
  public val types: List<String?>? = null,
  public val all: Boolean? = null,
  public val note: String? = null,
)
