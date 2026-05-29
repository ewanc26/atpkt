package uk.ewancroft.atpkt.generated.tools.ozone.queue

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.tools.ozone.team.Member

@Serializable
public data class QueueView(
  public val id: Long? = null,
  public val name: String? = null,
  public val subjectTypes: List<String?>? = null,
  public val collection: String? = null,
  public val reportTypes: List<String?>? = null,
  public val description: String? = null,
  public val createdBy: String? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
  public val enabled: Boolean? = null,
  public val deletedAt: String? = null,
  public val stats: QueueStats? = null,
)

@Serializable
public data class QueueStats(
  public val pendingCount: Long? = null,
  public val actionedCount: Long? = null,
  public val escalatedCount: Long? = null,
  public val inboundCount: Long? = null,
  public val actionRate: Long? = null,
  public val avgHandlingTimeSec: Long? = null,
  public val lastUpdated: String? = null,
)

@Serializable
public data class AssignmentView(
  public val id: Long? = null,
  public val did: String? = null,
  public val moderator: Member? = null,
  public val queue: QueueView? = null,
  public val startAt: String? = null,
  public val endAt: String? = null,
)
