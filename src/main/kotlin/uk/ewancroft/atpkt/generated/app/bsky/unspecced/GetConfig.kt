package uk.ewancroft.atpkt.generated.app.bsky.unspecced

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class GetConfigOutput(
  public val unused: String? = null,
)

@Serializable
public data class LiveNowConfig(
  public val did: String? = null,
  public val domains: List<String?>? = null,
)
