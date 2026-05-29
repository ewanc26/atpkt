package uk.ewancroft.atpkt.generated.com.atproto.admin

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.com.atproto.server.InviteCode

@Serializable
public data class StatusAttr(
  public val applied: Boolean? = null,
  public val ref: String? = null,
)

@Serializable
public data class AccountView(
  public val did: String? = null,
  public val handle: String? = null,
  public val email: String? = null,
  public val relatedRecords: List<JsonElement?>? = null,
  public val indexedAt: String? = null,
  public val invitedBy: InviteCode? = null,
  public val invites: List<InviteCode?>? = null,
  public val invitesDisabled: Boolean? = null,
  public val emailConfirmedAt: String? = null,
  public val inviteNote: String? = null,
  public val deactivatedAt: String? = null,
  public val threatSignatures: List<ThreatSignature?>? = null,
)

@Serializable
public data class RepoRef(
  public val did: String? = null,
)

@Serializable
public data class RepoBlobRef(
  public val did: String? = null,
  public val cid: String? = null,
  public val recordUri: String? = null,
)

@Serializable
public data class ThreatSignature(
  public val `property`: String? = null,
  public val `value`: String? = null,
)
