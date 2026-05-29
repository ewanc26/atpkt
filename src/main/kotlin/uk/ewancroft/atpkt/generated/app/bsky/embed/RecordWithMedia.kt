package uk.ewancroft.atpkt.generated.app.bsky.embed

import kotlinx.serialization.Serializable

@Serializable
public sealed interface MediaUnion

@Serializable
public data class RecordWithMedia(
  public val record: Record? = null,
  public val media: MediaUnion? = null,
)

@Serializable
public data class View(
  public val record: View? = null,
  public val media: MediaUnion? = null,
)
