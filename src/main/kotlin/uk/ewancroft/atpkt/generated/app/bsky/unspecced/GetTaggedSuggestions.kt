package uk.ewancroft.atpkt.generated.app.bsky.unspecced

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class GetTaggedSuggestionsOutput(
  public val unused: String? = null,
)

@Serializable
public data class Suggestion(
  public val tag: String? = null,
  public val subjectType: String? = null,
  public val subject: String? = null,
)
