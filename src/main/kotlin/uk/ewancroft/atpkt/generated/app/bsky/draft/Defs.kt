package uk.ewancroft.atpkt.generated.app.bsky.draft

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class DraftWithId(
  public val unused: String? = null,
)
@Serializable
public sealed interface PostgateEmbeddingRulesUnion
@Serializable
public sealed interface ThreadgateAllowUnion
@Serializable
public data class Draft(
  public val unused: String? = null,
)
@Serializable
public sealed interface LabelsUnion
@Serializable
public data class DraftPost(
  public val unused: String? = null,
)
@Serializable
public data class DraftView(
  public val unused: String? = null,
)
@Serializable
public data class DraftEmbedLocalRef(
  public val unused: String? = null,
)
@Serializable
public data class DraftEmbedCaption(
  public val unused: String? = null,
)
@Serializable
public data class DraftEmbedImage(
  public val unused: String? = null,
)
@Serializable
public data class DraftEmbedVideo(
  public val unused: String? = null,
)
@Serializable
public data class DraftEmbedExternal(
  public val unused: String? = null,
)
@Serializable
public data class DraftEmbedRecord(
  public val unused: String? = null,
)
