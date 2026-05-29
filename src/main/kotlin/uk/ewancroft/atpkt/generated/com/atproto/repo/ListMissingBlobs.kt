package uk.ewancroft.atpkt.generated.com.atproto.repo

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class ListMissingBlobsOutput(
  public val unused: String? = null,
)

@Serializable
public data class RecordBlob(
  public val cid: String? = null,
  public val recordUri: String? = null,
)
