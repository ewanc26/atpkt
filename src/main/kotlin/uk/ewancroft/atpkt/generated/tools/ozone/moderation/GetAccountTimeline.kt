package uk.ewancroft.atpkt.generated.tools.ozone.moderation

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class GetAccountTimelineOutput(
  public val unused: String? = null,
)

@Serializable
public data class TimelineItem(
  public val day: String? = null,
  public val summary: List<TimelineItemSummary?>? = null,
)

@Serializable
public data class TimelineItemSummary(
  public val eventSubjectType: String? = null,
  public val eventType: String? = null,
  public val count: Long? = null,
)
