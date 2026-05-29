package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Repost(
  public val subject: Com.atproto.repo.strongRef,
  public val createdAt: String,
  public val via: Com.atproto.repo.strongRef,
)
