package uk.ewancroft.atpkt.generated.com.atproto.server

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class InviteCode(
  public val code: String? = null,
  public val available: Long? = null,
  public val disabled: Boolean? = null,
  public val forAccount: String? = null,
  public val createdBy: String? = null,
  public val createdAt: String? = null,
  public val uses: List<InviteCodeUse?>? = null,
)

@Serializable
public data class InviteCodeUse(
  public val usedBy: String? = null,
  public val usedAt: String? = null,
)
