package uk.ewancroft.atpkt.generated.com.atproto.server

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class CreateInviteCodesInput(
  public val unused: String? = null,
)

@Serializable
public data class CreateInviteCodesOutput(
  public val unused: String? = null,
)

@Serializable
public data class AccountCodes(
  public val account: String? = null,
  public val codes: List<String?>? = null,
)
