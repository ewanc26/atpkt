package uk.ewancroft.atpkt.generated.app.bsky.notification

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class RecordDeleted()

@Serializable
public data class ChatPreference(
  public val include: String? = null,
  public val push: Boolean? = null,
)

@Serializable
public data class FilterablePreference(
  public val include: String? = null,
  public val list: Boolean? = null,
  public val push: Boolean? = null,
)

@Serializable
public data class Preference(
  public val list: Boolean? = null,
  public val push: Boolean? = null,
)

@Serializable
public data class Preferences(
  public val chat: ChatPreference? = null,
  public val follow: FilterablePreference? = null,
  public val like: FilterablePreference? = null,
  public val likeViaRepost: FilterablePreference? = null,
  public val mention: FilterablePreference? = null,
  public val quote: FilterablePreference? = null,
  public val reply: FilterablePreference? = null,
  public val repost: FilterablePreference? = null,
  public val repostViaRepost: FilterablePreference? = null,
  public val starterpackJoined: Preference? = null,
  public val subscribedPost: Preference? = null,
  public val unverified: Preference? = null,
  public val verified: Preference? = null,
)

@Serializable
public data class ActivitySubscription(
  public val post: Boolean? = null,
  public val reply: Boolean? = null,
)

@Serializable
public data class SubjectActivitySubscription(
  public val subject: String? = null,
  public val activitySubscription: ActivitySubscription? = null,
)
