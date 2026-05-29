package uk.ewancroft.atpkt.generated.app.bsky.labeler

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileView
import uk.ewancroft.atpkt.generated.com.atproto.label.Label
import uk.ewancroft.atpkt.generated.com.atproto.label.LabelValue
import uk.ewancroft.atpkt.generated.com.atproto.label.LabelValueDefinition
import uk.ewancroft.atpkt.generated.com.atproto.moderation.ReasonType
import uk.ewancroft.atpkt.generated.com.atproto.moderation.SubjectType

@Serializable
public data class LabelerView(
  public val uri: String? = null,
  public val cid: String? = null,
  public val creator: ProfileView? = null,
  public val likeCount: Long? = null,
  public val viewer: LabelerViewerState? = null,
  public val indexedAt: String? = null,
  public val labels: List<Label?>? = null,
)

@Serializable
public data class LabelerViewDetailed(
  public val uri: String? = null,
  public val cid: String? = null,
  public val creator: ProfileView? = null,
  public val policies: LabelerPolicies? = null,
  public val likeCount: Long? = null,
  public val viewer: LabelerViewerState? = null,
  public val indexedAt: String? = null,
  public val labels: List<Label?>? = null,
  public val reasonTypes: List<ReasonType?>? = null,
  public val subjectTypes: List<SubjectType?>? = null,
  public val subjectCollections: List<String?>? = null,
)

@Serializable
public data class LabelerViewerState(
  public val like: String? = null,
)

@Serializable
public data class LabelerPolicies(
  public val labelValues: List<LabelValue?>? = null,
  public val labelValueDefinitions: List<LabelValueDefinition?>? = null,
)
