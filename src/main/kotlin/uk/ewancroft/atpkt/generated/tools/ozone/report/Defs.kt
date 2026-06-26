package uk.ewancroft.atpkt.generated.tools.ozone.report

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class ReportAssignment(
  public val unused: String? = null,
)
@Serializable
public data class ReportView(
  public val unused: String? = null,
)
@Serializable
public data class QueueActivity(
  public val unused: String? = null,
)
@Serializable
public data class AssignmentActivity(
  public val unused: String? = null,
)
@Serializable
public data class EscalationActivity(
  public val unused: String? = null,
)
@Serializable
public data class CloseActivity(
  public val unused: String? = null,
)
@Serializable
public data class ReopenActivity(
  public val unused: String? = null,
)
@Serializable
public data class NoteActivity(
  public val unused: String? = null,
)
@Serializable
public sealed interface ActivityUnion
@Serializable
public data class ReportActivityView(
  public val unused: String? = null,
)
@Serializable
public data class LiveStats(
  public val unused: String? = null,
)
@Serializable
public data class HistoricalStats(
  public val unused: String? = null,
)
@Serializable
public data class AssignmentView(
  public val unused: String? = null,
)
