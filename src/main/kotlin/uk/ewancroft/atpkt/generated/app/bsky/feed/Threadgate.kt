package uk.ewancroft.atpkt.generated.app.bsky.feed

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Threadgate(
  public val unused: String? = null,
)

@Serializable
public data class MentionRule()

@Serializable
public data class FollowerRule()

@Serializable
public data class FollowingRule()

@Serializable
public data class ListRule(
  public val list: String? = null,
)
