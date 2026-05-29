package uk.ewancroft.atpkt.generated.tools.ozone.signature

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.com.atproto.admin.AccountView

@Serializable
public data class FindRelatedAccountsOutput(
  public val unused: String? = null,
)

@Serializable
public data class RelatedAccount(
  public val account: AccountView? = null,
  public val similarities: List<SigDetail?>? = null,
)
