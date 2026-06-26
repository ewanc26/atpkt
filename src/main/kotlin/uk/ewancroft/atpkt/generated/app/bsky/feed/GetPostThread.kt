package uk.ewancroft.atpkt.generated.app.bsky.feed

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class GetPostThreadOutput(
  public val thread: JsonElement,
  public val threadgate: ThreadgateView? = null,
)
