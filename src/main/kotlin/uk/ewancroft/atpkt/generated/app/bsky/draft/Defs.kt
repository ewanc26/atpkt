package uk.ewancroft.atpkt.generated.app.bsky.draft

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.generated.com.atproto.repo.StrongRef

@Serializable
public data class DraftWithId(
  public val id: String? = null,
  public val draft: Draft? = null,
)

@Serializable
public sealed interface PostgateEmbeddingRulesUnion

@Serializable
public sealed interface ThreadgateAllowUnion

@Serializable
public data class Draft(
  public val deviceId: String? = null,
  public val deviceName: String? = null,
  public val posts: List<DraftPost?>? = null,
  public val langs: List<String?>? = null,
  public val postgateEmbeddingRules: List<PostgateEmbeddingRulesUnion?>? = null,
  public val threadgateAllow: List<ThreadgateAllowUnion?>? = null,
)

@Serializable
public sealed interface LabelsUnion

@Serializable
public data class DraftPost(
  public val text: String? = null,
  public val labels: LabelsUnion? = null,
  public val embedImages: List<DraftEmbedImage?>? = null,
  public val embedVideos: List<DraftEmbedVideo?>? = null,
  public val embedExternals: List<DraftEmbedExternal?>? = null,
  public val embedRecords: List<DraftEmbedRecord?>? = null,
)

@Serializable
public data class DraftView(
  public val id: String? = null,
  public val draft: Draft? = null,
  public val createdAt: String? = null,
  public val updatedAt: String? = null,
)

@Serializable
public data class DraftEmbedLocalRef(
  public val path: String? = null,
)

@Serializable
public data class DraftEmbedCaption(
  public val lang: String? = null,
  public val content: String? = null,
)

@Serializable
public data class DraftEmbedImage(
  public val localRef: DraftEmbedLocalRef? = null,
  public val alt: String? = null,
)

@Serializable
public data class DraftEmbedVideo(
  public val localRef: DraftEmbedLocalRef? = null,
  public val alt: String? = null,
  public val captions: List<DraftEmbedCaption?>? = null,
)

@Serializable
public data class DraftEmbedExternal(
  public val uri: String? = null,
)

@Serializable
public data class DraftEmbedRecord(
  public val record: StrongRef? = null,
)
