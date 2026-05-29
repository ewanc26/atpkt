package uk.ewancroft.atpkt.generated.com.atproto.sync

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class ListReposOutput(
  public val unused: String? = null,
)

@Serializable
public data class Repo(
  public val did: String? = null,
  public val head: String? = null,
  public val rev: String? = null,
  public val active: Boolean? = null,
  public val status: String? = null,
)
