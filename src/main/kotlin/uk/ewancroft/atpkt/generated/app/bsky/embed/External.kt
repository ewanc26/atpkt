package uk.ewancroft.atpkt.generated.app.bsky.embed

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewBasic
import uk.ewancroft.atpkt.generated.com.atproto.label.Label
import uk.ewancroft.atpkt.generated.com.atproto.repo.StrongRef

@Serializable
public data class External(
  public val `external`: External? = null,
)

@Serializable
public data class External(
  public val uri: String? = null,
  public val title: String? = null,
  public val description: String? = null,
  public val thumb: JsonElement? = null,
  public val associatedRefs: List<StrongRef?>? = null,
)

@Serializable
public data class View(
  public val `external`: ViewExternal? = null,
)

@Serializable
public data class ViewExternal(
  public val uri: String? = null,
  public val title: String? = null,
  public val description: String? = null,
  public val thumb: String? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
  public val readingTime: Long? = null,
  public val labels: List<Label?>? = null,
  public val source: ViewExternalSource? = null,
  public val associatedRefs: List<StrongRef?>? = null,
  public val associatedProfiles: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class ViewExternalSource(
  public val uri: String? = null,
  public val icon: String? = null,
  public val title: String? = null,
  public val description: String? = null,
  public val theme: ViewExternalSourceTheme? = null,
)

@Serializable
public data class ViewExternalSourceTheme(
  public val backgroundRGB: ColorRGB? = null,
  public val foregroundRGB: ColorRGB? = null,
  public val accentRGB: ColorRGB? = null,
  public val accentForegroundRGB: ColorRGB? = null,
)

@Serializable
public data class ColorRGB(
  public val r: Long? = null,
  public val g: Long? = null,
  public val b: Long? = null,
)
