package uk.ewancroft.atpkt.generated.app.bsky.embed

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public sealed interface MediaUnion
@Serializable
public data class RecordWithMedia(
  public val unused: String? = null,
)
@Serializable
public data class RecordWithMediaView(
  public val unused: String? = null,
)
