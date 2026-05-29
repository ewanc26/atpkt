package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Document(
  public val bskyPostRef: Com.atproto.repo.strongRef,
  public val content: ContentUnion,
  public val contributors: List<Contributor>,
  public val coverImage: JsonElement,
  public val description: String,
  public val labels: LabelsUnion,
  public val links: LinksUnion,
  public val path: String,
  public val publishedAt: String,
  public val site: String,
  public val tags: List<String>,
  public val textContent: String,
  public val title: String,
  public val updatedAt: String,
)

@Serializable
public sealed interface ContentUnion

@Serializable
public sealed interface LabelsUnion

@Serializable
public sealed interface LinksUnion

@Serializable
public sealed interface ContentUnion

@Serializable
public sealed interface LabelsUnion

@Serializable
public sealed interface LinksUnion
