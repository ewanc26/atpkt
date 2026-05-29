package uk.ewancroft.atpkt.generated.app.bsky.ageassurance

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

@Serializable
public data class State(
  public val lastInitiatedAt: String? = null,
  public val status: Status? = null,
  public val access: Access? = null,
)

@Serializable
public data class StateMetadata(
  public val accountCreatedAt: String? = null,
)

@Serializable
public data class Config(
  public val regions: List<ConfigRegion?>? = null,
)

@Serializable
public sealed interface RulesUnion

@Serializable
public data class ConfigRegion(
  public val countryCode: String? = null,
  public val regionCode: String? = null,
  public val minAccessAge: Long? = null,
  public val rules: List<RulesUnion?>? = null,
)

@Serializable
public data class ConfigRegionRuleDefault(
  public val access: Access? = null,
)

@Serializable
public data class ConfigRegionRuleIfDeclaredOverAge(
  public val age: Long? = null,
  public val access: Access? = null,
)

@Serializable
public data class ConfigRegionRuleIfDeclaredUnderAge(
  public val age: Long? = null,
  public val access: Access? = null,
)

@Serializable
public data class ConfigRegionRuleIfAssuredOverAge(
  public val age: Long? = null,
  public val access: Access? = null,
)

@Serializable
public data class ConfigRegionRuleIfAssuredUnderAge(
  public val age: Long? = null,
  public val access: Access? = null,
)

@Serializable
public data class ConfigRegionRuleIfAccountNewerThan(
  public val date: String? = null,
  public val access: Access? = null,
)

@Serializable
public data class ConfigRegionRuleIfAccountOlderThan(
  public val date: String? = null,
  public val access: Access? = null,
)

@Serializable
public data class Event(
  public val createdAt: String? = null,
  public val attemptId: String? = null,
  public val status: String? = null,
  public val access: String? = null,
  public val countryCode: String? = null,
  public val regionCode: String? = null,
  public val email: String? = null,
  public val initIp: String? = null,
  public val initUa: String? = null,
  public val completeIp: String? = null,
  public val completeUa: String? = null,
)
