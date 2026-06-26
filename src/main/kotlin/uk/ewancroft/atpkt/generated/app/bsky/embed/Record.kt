package uk.ewancroft.atpkt.generated.app.bsky.embed

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class Record(
  public val unused: String? = null,
)
@Serializable
public sealed interface RecordUnion
@Serializable
public data class RecordView(
  public val unused: String? = null,
)
@Serializable
public sealed interface EmbedsUnion
@Serializable
public data class ViewRecord(
  public val unused: String? = null,
)
@Serializable
public data class ViewNotFound(
  public val unused: String? = null,
)
@Serializable
public data class ViewBlocked(
  public val unused: String? = null,
)
@Serializable
public data class ViewDetached(
  public val unused: String? = null,
)
