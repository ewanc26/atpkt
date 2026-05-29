package uk.ewancroft.atpkt.generated.tools.ozone.setting

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Option(
  public val key: String? = null,
  public val did: String? = null,
  public val `value`: JsonElement? = null,
  public val description: String? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
  public val managerRole: String? = null,
  public val scope: String? = null,
  public val createdBy: String? = null,
  public val lastUpdatedBy: String? = null,
)
