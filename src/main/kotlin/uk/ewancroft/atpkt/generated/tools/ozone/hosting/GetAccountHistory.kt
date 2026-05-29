package uk.ewancroft.atpkt.generated.tools.ozone.hosting

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetAccountHistoryOutput(
  public val unused: String? = null,
)

@Serializable
public sealed interface DetailsUnion

@Serializable
public data class Event(
  public val details: DetailsUnion? = null,
  public val createdBy: String? = null,
  public val createdAt: String? = null,
)

@Serializable
public data class AccountCreated(
  public val email: String? = null,
  public val handle: String? = null,
)

@Serializable
public data class EmailUpdated(
  public val email: String? = null,
)

@Serializable
public data class EmailConfirmed(
  public val email: String? = null,
)

@Serializable
public data class PasswordUpdated()

@Serializable
public data class HandleUpdated(
  public val handle: String? = null,
)
