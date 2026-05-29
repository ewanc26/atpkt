package uk.ewancroft.atpkt.generated.tools.ozone.report

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.com.atproto.moderation.ReasonType
import uk.ewancroft.atpkt.generated.tools.ozone.moderation.ModEventView
import uk.ewancroft.atpkt.generated.tools.ozone.moderation.SubjectStatusView
import uk.ewancroft.atpkt.generated.tools.ozone.moderation.SubjectView
import uk.ewancroft.atpkt.generated.tools.ozone.queue.QueueView
import uk.ewancroft.atpkt.generated.tools.ozone.team.Member

@Serializable
public data class ReportAssignment(
  public val did: String? = null,
  public val moderator: Member? = null,
  public val assignedAt: String? = null,
)

@Serializable
public data class ReportView(
  public val id: Long? = null,
  public val eventId: Long? = null,
  public val status: String? = null,
  public val subject: SubjectView? = null,
  public val reportType: ReasonType? = null,
  public val reportedBy: String? = null,
  public val reporter: SubjectView? = null,
  public val comment: String? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
  public val queuedAt: String? = null,
  public val actionEventIds: List<Long?>? = null,
  public val actions: List<ModEventView?>? = null,
  public val actionNote: String? = null,
  public val subjectStatus: SubjectStatusView? = null,
  public val relatedReportCount: Long? = null,
  public val assignment: ReportAssignment? = null,
  public val queue: QueueView? = null,
  public val isMuted: Boolean? = null,
)

@Serializable
public data class QueueActivity(
  public val previousStatus: String? = null,
)

@Serializable
public data class AssignmentActivity(
  public val previousStatus: String? = null,
)

@Serializable
public data class EscalationActivity(
  public val previousStatus: String? = null,
)

@Serializable
public data class CloseActivity(
  public val previousStatus: String? = null,
)

@Serializable
public data class ReopenActivity(
  public val previousStatus: String? = null,
)

@Serializable
public data class NoteActivity()

@Serializable
public sealed interface ActivityUnion

@Serializable
public data class ReportActivityView(
  public val id: Long? = null,
  public val reportId: Long? = null,
  public val activity: ActivityUnion? = null,
  public val internalNote: String? = null,
  public val publicNote: String? = null,
  public val meta: JsonElement? = null,
  public val isAutomated: Boolean? = null,
  public val createdBy: String? = null,
  public val moderator: Member? = null,
  public val createdAt: String? = null,
)

@Serializable
public data class LiveStats(
  public val pendingCount: Long? = null,
  public val actionedCount: Long? = null,
  public val escalatedCount: Long? = null,
  public val inboundCount: Long? = null,
  public val actionRate: Long? = null,
  public val avgHandlingTimeSec: Long? = null,
  public val lastUpdated: String? = null,
)

@Serializable
public data class HistoricalStats(
  public val date: String? = null,
  public val computedAt: String? = null,
  public val pendingCount: Long? = null,
  public val actionedCount: Long? = null,
  public val escalatedCount: Long? = null,
  public val inboundCount: Long? = null,
  public val actionRate: Long? = null,
  public val avgHandlingTimeSec: Long? = null,
)

@Serializable
public data class AssignmentView(
  public val id: Long? = null,
  public val did: String? = null,
  public val moderator: Member? = null,
  public val queue: QueueView? = null,
  public val reportId: Long? = null,
  public val startAt: String? = null,
  public val endAt: String? = null,
)
