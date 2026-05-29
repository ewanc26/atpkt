package uk.ewancroft.atpkt.generated.tools.ozone.moderation

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.app.bsky.ageassurance.Access
import uk.ewancroft.atpkt.generated.com.atproto.admin.ThreatSignature
import uk.ewancroft.atpkt.generated.com.atproto.label.Label
import uk.ewancroft.atpkt.generated.com.atproto.moderation.ReasonType
import uk.ewancroft.atpkt.generated.com.atproto.moderation.SubjectType
import uk.ewancroft.atpkt.generated.com.atproto.server.InviteCode

@Serializable
public sealed interface EventUnion

@Serializable
public sealed interface SubjectUnion

@Serializable
public data class ModEventView(
  public val id: Long? = null,
  public val event: EventUnion? = null,
  public val subject: SubjectUnion? = null,
  public val subjectBlobCids: List<String?>? = null,
  public val createdBy: String? = null,
  public val createdAt: String? = null,
  public val creatorHandle: String? = null,
  public val subjectHandle: String? = null,
  public val modTool: ModTool? = null,
)

@Serializable
public data class ModEventViewDetail(
  public val id: Long? = null,
  public val event: EventUnion? = null,
  public val subject: SubjectUnion? = null,
  public val subjectBlobs: List<BlobView?>? = null,
  public val createdBy: String? = null,
  public val createdAt: String? = null,
  public val modTool: ModTool? = null,
)

@Serializable
public sealed interface HostingUnion

@Serializable
public data class SubjectStatusView(
  public val id: Long? = null,
  public val subject: SubjectUnion? = null,
  public val hosting: HostingUnion? = null,
  public val subjectBlobCids: List<String?>? = null,
  public val subjectRepoHandle: String? = null,
  public val updatedAt: String? = null,
  public val createdAt: String? = null,
  public val reviewState: SubjectReviewState? = null,
  public val comment: String? = null,
  public val priorityScore: Long? = null,
  public val muteUntil: String? = null,
  public val muteReportingUntil: String? = null,
  public val lastReviewedBy: String? = null,
  public val lastReviewedAt: String? = null,
  public val lastReportedAt: String? = null,
  public val lastAppealedAt: String? = null,
  public val takendown: Boolean? = null,
  public val appealed: Boolean? = null,
  public val suspendUntil: String? = null,
  public val tags: List<String?>? = null,
  public val accountStats: AccountStats? = null,
  public val recordsStats: RecordsStats? = null,
  public val accountStrike: AccountStrike? = null,
  public val ageAssuranceState: String? = null,
  public val ageAssuranceUpdatedBy: String? = null,
)

@Serializable
public sealed interface ProfileUnion

@Serializable
public data class SubjectView(
  public val type: SubjectType? = null,
  public val subject: String? = null,
  public val status: SubjectStatusView? = null,
  public val repo: RepoViewDetail? = null,
  public val profile: ProfileUnion? = null,
  public val record: RecordViewDetail? = null,
)

@Serializable
public data class AccountStats(
  public val reportCount: Long? = null,
  public val appealCount: Long? = null,
  public val suspendCount: Long? = null,
  public val escalateCount: Long? = null,
  public val takedownCount: Long? = null,
)

@Serializable
public data class RecordsStats(
  public val totalReports: Long? = null,
  public val reportedCount: Long? = null,
  public val escalatedCount: Long? = null,
  public val appealedCount: Long? = null,
  public val subjectCount: Long? = null,
  public val pendingCount: Long? = null,
  public val processedCount: Long? = null,
  public val takendownCount: Long? = null,
)

@Serializable
public data class AccountStrike(
  public val activeStrikeCount: Long? = null,
  public val totalStrikeCount: Long? = null,
  public val firstStrikeAt: String? = null,
  public val lastStrikeAt: String? = null,
)

@Serializable
public data class ModEventTakedown(
  public val comment: String? = null,
  public val durationInHours: Long? = null,
  public val acknowledgeAccountSubjects: Boolean? = null,
  public val policies: List<String?>? = null,
  public val severityLevel: String? = null,
  public val targetServices: List<String?>? = null,
  public val strikeCount: Long? = null,
  public val strikeExpiresAt: String? = null,
)

@Serializable
public data class ModEventReverseTakedown(
  public val comment: String? = null,
  public val policies: List<String?>? = null,
  public val severityLevel: String? = null,
  public val strikeCount: Long? = null,
)

@Serializable
public data class ModEventResolveAppeal(
  public val comment: String? = null,
)

@Serializable
public data class ModEventComment(
  public val comment: String? = null,
  public val sticky: Boolean? = null,
)

@Serializable
public data class ModEventReport(
  public val comment: String? = null,
  public val isReporterMuted: Boolean? = null,
  public val reportType: ReasonType? = null,
)

@Serializable
public data class ModEventLabel(
  public val comment: String? = null,
  public val createLabelVals: List<String?>? = null,
  public val negateLabelVals: List<String?>? = null,
  public val durationInHours: Long? = null,
)

@Serializable
public data class ModEventPriorityScore(
  public val comment: String? = null,
  public val score: Long? = null,
)

@Serializable
public data class AgeAssuranceEvent(
  public val createdAt: String? = null,
  public val attemptId: String? = null,
  public val status: String? = null,
  public val access: Access? = null,
  public val countryCode: String? = null,
  public val regionCode: String? = null,
  public val initIp: String? = null,
  public val initUa: String? = null,
  public val completeIp: String? = null,
  public val completeUa: String? = null,
)

@Serializable
public data class AgeAssuranceOverrideEvent(
  public val status: String? = null,
  public val access: Access? = null,
  public val comment: String? = null,
)

@Serializable
public data class AgeAssurancePurgeEvent(
  public val comment: String? = null,
)

@Serializable
public data class RevokeAccountCredentialsEvent(
  public val comment: String? = null,
)

@Serializable
public data class ModEventAcknowledge(
  public val comment: String? = null,
  public val acknowledgeAccountSubjects: Boolean? = null,
)

@Serializable
public data class ModEventEscalate(
  public val comment: String? = null,
)

@Serializable
public data class ModEventMute(
  public val comment: String? = null,
  public val durationInHours: Long? = null,
)

@Serializable
public data class ModEventUnmute(
  public val comment: String? = null,
)

@Serializable
public data class ModEventMuteReporter(
  public val comment: String? = null,
  public val durationInHours: Long? = null,
)

@Serializable
public data class ModEventUnmuteReporter(
  public val comment: String? = null,
)

@Serializable
public data class ModEventEmail(
  public val subjectLine: String? = null,
  public val content: String? = null,
  public val comment: String? = null,
  public val policies: List<String?>? = null,
  public val severityLevel: String? = null,
  public val strikeCount: Long? = null,
  public val strikeExpiresAt: String? = null,
  public val isDelivered: Boolean? = null,
)

@Serializable
public data class ModEventDivert(
  public val comment: String? = null,
)

@Serializable
public data class ModEventTag(
  public val add: List<String?>? = null,
  public val remove: List<String?>? = null,
  public val comment: String? = null,
  public val durationInHours: Long? = null,
)

@Serializable
public data class AccountEvent(
  public val comment: String? = null,
  public val active: Boolean? = null,
  public val status: String? = null,
  public val timestamp: String? = null,
)

@Serializable
public data class IdentityEvent(
  public val comment: String? = null,
  public val handle: String? = null,
  public val pdsHost: String? = null,
  public val tombstone: Boolean? = null,
  public val timestamp: String? = null,
)

@Serializable
public data class RecordEvent(
  public val comment: String? = null,
  public val op: String? = null,
  public val cid: String? = null,
  public val timestamp: String? = null,
)

@Serializable
public data class ScheduleTakedownEvent(
  public val comment: String? = null,
  public val executeAt: String? = null,
  public val executeAfter: String? = null,
  public val executeUntil: String? = null,
)

@Serializable
public data class CancelScheduledTakedownEvent(
  public val comment: String? = null,
)

@Serializable
public data class RepoView(
  public val did: String? = null,
  public val handle: String? = null,
  public val email: String? = null,
  public val relatedRecords: List<JsonElement?>? = null,
  public val indexedAt: String? = null,
  public val moderation: Moderation? = null,
  public val invitedBy: InviteCode? = null,
  public val invitesDisabled: Boolean? = null,
  public val inviteNote: String? = null,
  public val deactivatedAt: String? = null,
  public val threatSignatures: List<ThreatSignature?>? = null,
)

@Serializable
public data class RepoViewDetail(
  public val did: String? = null,
  public val handle: String? = null,
  public val email: String? = null,
  public val relatedRecords: List<JsonElement?>? = null,
  public val indexedAt: String? = null,
  public val moderation: ModerationDetail? = null,
  public val labels: List<Label?>? = null,
  public val invitedBy: InviteCode? = null,
  public val invites: List<InviteCode?>? = null,
  public val invitesDisabled: Boolean? = null,
  public val inviteNote: String? = null,
  public val emailConfirmedAt: String? = null,
  public val deactivatedAt: String? = null,
  public val threatSignatures: List<ThreatSignature?>? = null,
)

@Serializable
public data class RepoViewNotFound(
  public val did: String? = null,
)

@Serializable
public data class RecordView(
  public val uri: String? = null,
  public val cid: String? = null,
  public val `value`: JsonElement? = null,
  public val blobCids: List<String?>? = null,
  public val indexedAt: String? = null,
  public val moderation: Moderation? = null,
  public val repo: RepoView? = null,
)

@Serializable
public data class RecordViewDetail(
  public val uri: String? = null,
  public val cid: String? = null,
  public val `value`: JsonElement? = null,
  public val blobs: List<BlobView?>? = null,
  public val labels: List<Label?>? = null,
  public val indexedAt: String? = null,
  public val moderation: ModerationDetail? = null,
  public val repo: RepoView? = null,
)

@Serializable
public data class RecordViewNotFound(
  public val uri: String? = null,
)

@Serializable
public data class Moderation(
  public val subjectStatus: SubjectStatusView? = null,
)

@Serializable
public data class ModerationDetail(
  public val subjectStatus: SubjectStatusView? = null,
)

@Serializable
public sealed interface DetailsUnion

@Serializable
public data class BlobView(
  public val cid: String? = null,
  public val mimeType: String? = null,
  public val size: Long? = null,
  public val createdAt: String? = null,
  public val details: DetailsUnion? = null,
  public val moderation: Moderation? = null,
)

@Serializable
public data class ImageDetails(
  public val width: Long? = null,
  public val height: Long? = null,
)

@Serializable
public data class VideoDetails(
  public val width: Long? = null,
  public val height: Long? = null,
  public val length: Long? = null,
)

@Serializable
public data class AccountHosting(
  public val status: String? = null,
  public val updatedAt: String? = null,
  public val createdAt: String? = null,
  public val deletedAt: String? = null,
  public val deactivatedAt: String? = null,
  public val reactivatedAt: String? = null,
)

@Serializable
public data class RecordHosting(
  public val status: String? = null,
  public val updatedAt: String? = null,
  public val createdAt: String? = null,
  public val deletedAt: String? = null,
)

@Serializable
public data class ReporterStats(
  public val did: String? = null,
  public val accountReportCount: Long? = null,
  public val recordReportCount: Long? = null,
  public val reportedAccountCount: Long? = null,
  public val reportedRecordCount: Long? = null,
  public val takendownAccountCount: Long? = null,
  public val takendownRecordCount: Long? = null,
  public val labeledAccountCount: Long? = null,
  public val labeledRecordCount: Long? = null,
)

@Serializable
public data class ModTool(
  public val name: String? = null,
  public val meta: JsonElement? = null,
)

@Serializable
public data class ScheduledActionView(
  public val id: Long? = null,
  public val action: String? = null,
  public val eventData: JsonElement? = null,
  public val did: String? = null,
  public val executeAt: String? = null,
  public val executeAfter: String? = null,
  public val executeUntil: String? = null,
  public val randomizeExecution: Boolean? = null,
  public val createdBy: String? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
  public val status: String? = null,
  public val lastExecutedAt: String? = null,
  public val lastFailureReason: String? = null,
  public val executionEventId: Long? = null,
)
