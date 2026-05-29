package uk.ewancroft.atpkt.generated.com.atproto.identity

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class IdentityInfo(
  public val did: String? = null,
  public val handle: String? = null,
  public val didDoc: JsonElement? = null,
)
