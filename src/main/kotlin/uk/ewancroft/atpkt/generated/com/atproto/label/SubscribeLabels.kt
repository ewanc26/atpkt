package uk.ewancroft.atpkt.generated.com.atproto.label

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class Labels(
  public val seq: Long? = null,
  public val labels: List<Label?>? = null,
)

@Serializable
public data class Info(
  public val name: String? = null,
  public val message: String? = null,
)
