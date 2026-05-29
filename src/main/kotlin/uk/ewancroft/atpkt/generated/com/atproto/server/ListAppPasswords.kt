package uk.ewancroft.atpkt.generated.com.atproto.server

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class ListAppPasswordsOutput(
  public val unused: String? = null,
)

@Serializable
public data class AppPassword(
  public val name: String? = null,
  public val createdAt: String? = null,
  public val privileged: Boolean? = null,
)
