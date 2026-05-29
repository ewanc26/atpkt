package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Follow(
  public val subject: String,
  public val createdAt: String,
  public val via: Com.atproto.repo.strongRef,
)
