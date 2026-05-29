package uk.ewancroft.atpkt.generated.tools.ozone.verification

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class RevokeVerificationsInput(
  public val unused: String? = null,
)

@Serializable
public data class RevokeVerificationsOutput(
  public val unused: String? = null,
)

@Serializable
public data class RevokeError(
  public val uri: String? = null,
  public val error: String? = null,
)
