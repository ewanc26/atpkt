package uk.ewancroft.atpkt.generated.com.atproto.sync

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class ListHostsOutput(
  public val unused: String? = null,
)

@Serializable
public data class Host(
  public val hostname: String? = null,
  public val seq: Long? = null,
  public val accountCount: Long? = null,
  public val status: HostStatus? = null,
)
