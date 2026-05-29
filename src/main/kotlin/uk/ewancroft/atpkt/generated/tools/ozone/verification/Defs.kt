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
  public val issuer: String? = null,
  public val uri: String? = null,
  public val subject: String? = null,
  public val handle: String? = null,
  public val displayName: String? = null,
  public val createdAt: String? = null,
  public val revokeReason: String? = null,
  public val revokedAt: String? = null,
  public val revokedBy: String? = null,
  public val subjectProfile: SubjectProfileUnion? = null,
  public val issuerProfile: IssuerProfileUnion? = null,
  public val subjectRepo: SubjectRepoUnion? = null,
  public val issuerRepo: IssuerRepoUnion? = null,
)
