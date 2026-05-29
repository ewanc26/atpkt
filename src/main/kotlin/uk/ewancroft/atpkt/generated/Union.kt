package uk.ewancroft.atpkt.generated

import kotlinx.serialization.Serializable

@Serializable
public data class Union(
  public val unionField: UnionFieldUnion,
)

@Serializable
public sealed interface UnionFieldUnion

@Serializable
public sealed interface UnionFieldUnion
