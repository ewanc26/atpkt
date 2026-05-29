package uk.ewancroft.atpkt.generated.app.bsky.embed

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Images(
  public val images: List<Image?>? = null,
)

@Serializable
public data class Image(
  public val image: JsonElement? = null,
  public val alt: String? = null,
  public val aspectRatio: AspectRatio? = null,
)

@Serializable
public data class View(
  public val images: List<ViewImage?>? = null,
)

@Serializable
public data class ViewImage(
  public val thumb: String? = null,
  public val fullsize: String? = null,
  public val alt: String? = null,
  public val aspectRatio: AspectRatio? = null,
)
