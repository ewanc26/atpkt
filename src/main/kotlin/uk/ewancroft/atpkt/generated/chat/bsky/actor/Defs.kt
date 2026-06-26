package uk.ewancroft.atpkt.generated.chat.bsky.actor

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public sealed interface KindUnion
@Serializable
public data class ProfileViewBasic(
  public val unused: String? = null,
)
@Serializable
public data class DirectConvoMember(
  public val unused: String? = null,
)
@Serializable
public data class GroupConvoMember(
  public val unused: String? = null,
)
@Serializable
public data class PastGroupConvoMember(
  public val unused: String? = null,
)
