package uk.ewancroft.atpkt.generated.app.bsky.unspecced

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetPostThreadOtherV2Output(
  public val unused: String? = null,
)
@Serializable
public sealed interface GetPostThreadOtherV2ValueUnion
@Serializable
public data class GetPostThreadOtherV2ThreadItem(
  public val unused: String? = null,
)
