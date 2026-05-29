package uk.ewancroft.atpkt.generated.app.bsky.embed

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Video(
  public val video: JsonElement? = null,
  public val captions: List<Caption?>? = null,
  public val alt: String? = null,
  public val aspectRatio: AspectRatio? = null,
  public val presentation: String? = null,
)

@Serializable
public data class Caption(
  public val lang: String? = null,
  public val `file`: JsonElement? = null,
)

@Serializable
public data class View(
  public val cid: String? = null,
  public val playlist: String? = null,
  public val thumbnail: String? = null,
  public val alt: String? = null,
  public val aspectRatio: AspectRatio? = null,
  public val presentation: String? = null,
)
