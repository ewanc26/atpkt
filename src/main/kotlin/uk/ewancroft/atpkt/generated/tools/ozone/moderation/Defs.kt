package uk.ewancroft.atpkt.generated.tools.ozone.moderation

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public sealed interface EventUnion
@Serializable
public sealed interface SubjectUnion
@Serializable
public data class ModEventView(
  public val unused: String? = null,
)
@Serializable
public data class ModEventViewDetail(
  public val unused: String? = null,
)
@Serializable
public sealed interface HostingUnion
@Serializable
public data class SubjectStatusView(
  public val unused: String? = null,
)
@Serializable
public sealed interface ProfileUnion
@Serializable
public data class SubjectView(
  public val unused: String? = null,
)
@Serializable
public data class AccountStats(
  public val unused: String? = null,
)
@Serializable
public data class RecordsStats(
  public val unused: String? = null,
)
@Serializable
public data class AccountStrike(
  public val unused: String? = null,
)
@Serializable
public data class ModEventTakedown(
  public val unused: String? = null,
)
@Serializable
public data class ModEventReverseTakedown(
  public val unused: String? = null,
)
@Serializable
public data class ModEventResolveAppeal(
  public val unused: String? = null,
)
@Serializable
public data class ModEventComment(
  public val unused: String? = null,
)
@Serializable
public data class ModEventReport(
  public val unused: String? = null,
)
@Serializable
public data class ModEventLabel(
  public val unused: String? = null,
)
@Serializable
public data class ModEventPriorityScore(
  public val unused: String? = null,
)
@Serializable
public data class AgeAssuranceEvent(
  public val unused: String? = null,
)
@Serializable
public data class AgeAssuranceOverrideEvent(
  public val unused: String? = null,
)
@Serializable
public data class AgeAssurancePurgeEvent(
  public val unused: String? = null,
)
@Serializable
public data class RevokeAccountCredentialsEvent(
  public val unused: String? = null,
)
@Serializable
public data class ModEventAcknowledge(
  public val unused: String? = null,
)
@Serializable
public data class ModEventEscalate(
  public val unused: String? = null,
)
@Serializable
public data class ModEventMute(
  public val unused: String? = null,
)
@Serializable
public data class ModEventUnmute(
  public val unused: String? = null,
)
@Serializable
public data class ModEventMuteReporter(
  public val unused: String? = null,
)
@Serializable
public data class ModEventUnmuteReporter(
  public val unused: String? = null,
)
@Serializable
public data class ModEventEmail(
  public val unused: String? = null,
)
@Serializable
public data class ModEventDivert(
  public val unused: String? = null,
)
@Serializable
public data class ModEventTag(
  public val unused: String? = null,
)
@Serializable
public data class AccountEvent(
  public val unused: String? = null,
)
@Serializable
public data class IdentityEvent(
  public val unused: String? = null,
)
@Serializable
public data class RecordEvent(
  public val unused: String? = null,
)
@Serializable
public data class ScheduleTakedownEvent(
  public val unused: String? = null,
)
@Serializable
public data class CancelScheduledTakedownEvent(
  public val unused: String? = null,
)
@Serializable
public data class RepoView(
  public val unused: String? = null,
)
@Serializable
public data class RepoViewDetail(
  public val unused: String? = null,
)
@Serializable
public data class RepoViewNotFound(
  public val unused: String? = null,
)
@Serializable
public data class RecordView(
  public val unused: String? = null,
)
@Serializable
public data class RecordViewDetail(
  public val unused: String? = null,
)
@Serializable
public data class RecordViewNotFound(
  public val unused: String? = null,
)
@Serializable
public data class Moderation(
  public val unused: String? = null,
)
@Serializable
public data class ModerationDetail(
  public val unused: String? = null,
)
@Serializable
public sealed interface DetailsUnion
@Serializable
public data class BlobView(
  public val unused: String? = null,
)
@Serializable
public data class ImageDetails(
  public val unused: String? = null,
)
@Serializable
public data class VideoDetails(
  public val unused: String? = null,
)
@Serializable
public data class AccountHosting(
  public val unused: String? = null,
)
@Serializable
public data class RecordHosting(
  public val unused: String? = null,
)
@Serializable
public data class ReporterStats(
  public val unused: String? = null,
)
@Serializable
public data class ModTool(
  public val unused: String? = null,
)
@Serializable
public data class ScheduledActionView(
  public val unused: String? = null,
)
