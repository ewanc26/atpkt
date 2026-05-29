package uk.ewancroft.atpkt.generated.tools.ozone.server

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetConfigOutput(
  public val unused: String? = null,
)

@Serializable
public data class ServiceConfig(
  public val url: String? = null,
)

@Serializable
public data class ViewerConfig(
  public val role: String? = null,
)
