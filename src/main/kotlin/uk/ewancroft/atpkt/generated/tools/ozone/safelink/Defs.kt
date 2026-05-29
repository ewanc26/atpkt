package uk.ewancroft.atpkt.generated.tools.ozone.safelink

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Event(
  public val id: Long? = null,
  public val eventType: EventType? = null,
  public val url: String? = null,
  public val pattern: PatternType? = null,
  public val action: ActionType? = null,
  public val reason: ReasonType? = null,
  public val createdBy: String? = null,
  public val createdAt: String? = null,
  public val comment: String? = null,
)

@Serializable
public data class UrlRule(
  public val url: String? = null,
  public val pattern: PatternType? = null,
  public val action: ActionType? = null,
  public val reason: ReasonType? = null,
  public val comment: String? = null,
  public val createdBy: String? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
)
