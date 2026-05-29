package uk.ewancroft.atpkt.generated.com.atproto.temp

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class CheckHandleAvailabilityOutput(
  public val unused: String? = null,
)

@Serializable
public data class ResultAvailable()

@Serializable
public data class ResultUnavailable(
  public val suggestions: List<Suggestion?>? = null,
)

@Serializable
public data class Suggestion(
  public val handle: String? = null,
  public val method: String? = null,
)
