package uk.ewancroft.atpkt.generated.tools.ozone.moderation

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class ScheduleActionInput(
  public val unused: String? = null,
)

@Serializable
public data class ScheduleActionOutput(
  public val unused: String? = null,
)

@Serializable
public data class Takedown(
  public val comment: String? = null,
  public val durationInHours: Long? = null,
  public val acknowledgeAccountSubjects: Boolean? = null,
  public val policies: List<String?>? = null,
  public val severityLevel: String? = null,
  public val strikeCount: Long? = null,
  public val strikeExpiresAt: String? = null,
  public val emailContent: String? = null,
  public val emailSubject: String? = null,
)

@Serializable
public data class SchedulingConfig(
  public val executeAt: String? = null,
  public val executeAfter: String? = null,
  public val executeUntil: String? = null,
)

@Serializable
public data class ScheduledActionResults(
  public val succeeded: List<String?>? = null,
  public val failed: List<FailedScheduling?>? = null,
)

@Serializable
public data class FailedScheduling(
  public val subject: String? = null,
  public val error: String? = null,
  public val errorCode: String? = null,
)
