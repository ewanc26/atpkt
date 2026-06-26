package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Document(
  public val unused: String? = null,
)
@Serializable
public sealed interface ContentUnion
@Serializable
public sealed interface DocumentLabelsUnion
@Serializable
public sealed interface LinksUnion
