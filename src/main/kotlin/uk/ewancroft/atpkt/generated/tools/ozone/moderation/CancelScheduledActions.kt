package uk.ewancroft.atpkt.generated.tools.ozone.moderation

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class CancelScheduledActionsInput(
  public val unused: String? = null,
)

@Serializable
public data class CancelScheduledActionsOutput(
  public val unused: String? = null,
)

@Serializable
public data class CancellationResults(
  public val succeeded: List<String?>? = null,
  public val failed: List<FailedCancellation?>? = null,
)

@Serializable
public data class FailedCancellation(
  public val did: String? = null,
  public val error: String? = null,
  public val errorCode: String? = null,
)
