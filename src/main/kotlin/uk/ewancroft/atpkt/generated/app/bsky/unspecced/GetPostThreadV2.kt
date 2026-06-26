package uk.ewancroft.atpkt.generated.app.bsky.unspecced

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetPostThreadV2Output(
  public val unused: String? = null,
)
@Serializable
public sealed interface GetPostThreadV2ValueUnion
@Serializable
public data class GetPostThreadV2ThreadItem(
  public val unused: String? = null,
)
