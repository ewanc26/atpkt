package uk.ewancroft.atpkt.generated.chat.bsky.moderation

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetActorMetadataOutput(
  public val unused: String? = null,
)

@Serializable
public data class Metadata(
  public val messagesSent: Long? = null,
  public val messagesReceived: Long? = null,
  public val convos: Long? = null,
  public val convosStarted: Long? = null,
)
