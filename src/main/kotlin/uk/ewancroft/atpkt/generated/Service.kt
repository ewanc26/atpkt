package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class Service(
  public val policies: LabelerPolicies,
  public val labels: LabelsUnion,
  public val createdAt: String,
  public val reasonTypes: List<ReasonType>,
  public val subjectTypes: List<SubjectType>,
  public val subjectCollections: List<String>,
)

@Serializable
public sealed interface LabelsUnion

@Serializable
public sealed interface LabelsUnion
