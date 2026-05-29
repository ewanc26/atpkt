package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class Postgate(
  public val createdAt: String,
  public val post: String,
  public val detachedEmbeddingUris: List<String>,
  public val embeddingRules: List<EmbeddingRulesUnion>,
)

@Serializable
public sealed interface EmbeddingRulesUnion

@Serializable
public sealed interface EmbeddingRulesUnion
