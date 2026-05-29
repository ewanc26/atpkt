package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Verification(
  public val subject: String,
  public val handle: String,
  public val displayName: String,
  public val createdAt: String,
)
