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
  public val unused: String? = null,
)
@Serializable
public data class AccountCreated(
  public val unused: String? = null,
)
@Serializable
public data class EmailUpdated(
  public val unused: String? = null,
)
@Serializable
public data class EmailConfirmed(
  public val unused: String? = null,
)
@Serializable
public data class PasswordUpdated(
  public val unused: String? = null,
)
@Serializable
public data class HandleUpdated(
  public val unused: String? = null,
)
