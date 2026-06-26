package uk.ewancroft.atpkt.generated.tools.ozone.verification

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public sealed interface SubjectProfileUnion
@Serializable
public sealed interface IssuerProfileUnion
@Serializable
public sealed interface SubjectRepoUnion
@Serializable
public sealed interface IssuerRepoUnion
@Serializable
public data class VerificationView(
  public val unused: String? = null,
)
