package uk.ewancroft.atpkt.generated.app.bsky.unspecced

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetPostThreadV2Output(
  public val unused: String? = null,
)

@Serializable
public sealed interface ValueUnion

@Serializable
public data class ThreadItem(
  public val uri: String? = null,
  public val depth: Long? = null,
  public val `value`: ValueUnion? = null,
)
