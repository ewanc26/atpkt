package uk.ewancroft.atpkt.generated.tools.ozone.`set`

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Set(
  public val name: String? = null,
  public val description: String? = null,
)

@Serializable
public data class SetView(
  public val name: String? = null,
  public val description: String? = null,
  public val setSize: Long? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
)
