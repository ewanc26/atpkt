package uk.ewancroft.atpkt.generated.app.bsky.ageassurance

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class State(
  public val unused: String? = null,
)
@Serializable
public data class StateMetadata(
  public val unused: String? = null,
)
@Serializable
public data class Config(
  public val unused: String? = null,
)
@Serializable
public sealed interface RulesUnion
@Serializable
public data class ConfigRegion(
  public val unused: String? = null,
)
@Serializable
public data class ConfigRegionRuleDefault(
  public val unused: String? = null,
)
@Serializable
public data class ConfigRegionRuleIfDeclaredOverAge(
  public val unused: String? = null,
)
@Serializable
public data class ConfigRegionRuleIfDeclaredUnderAge(
  public val unused: String? = null,
)
@Serializable
public data class ConfigRegionRuleIfAssuredOverAge(
  public val unused: String? = null,
)
@Serializable
public data class ConfigRegionRuleIfAssuredUnderAge(
  public val unused: String? = null,
)
@Serializable
public data class ConfigRegionRuleIfAccountNewerThan(
  public val unused: String? = null,
)
@Serializable
public data class ConfigRegionRuleIfAccountOlderThan(
  public val unused: String? = null,
)
@Serializable
public data class Event(
  public val unused: String? = null,
)
