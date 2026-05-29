package uk.ewancroft.atpkt.generated.tools.ozone.verification

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GrantVerificationsInput(
  public val unused: String? = null,
)

@Serializable
public data class GrantVerificationsOutput(
  public val unused: String? = null,
)

@Serializable
public data class VerificationInput(
  public val subject: String? = null,
  public val handle: String? = null,
  public val displayName: String? = null,
  public val createdAt: String? = null,
)

@Serializable
public data class GrantError(
  public val error: String? = null,
  public val subject: String? = null,
)
