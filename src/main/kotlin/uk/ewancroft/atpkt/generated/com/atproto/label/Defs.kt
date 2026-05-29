package uk.ewancroft.atpkt.generated.com.atproto.label

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Label(
  public val ver: Long? = null,
  public val src: String? = null,
  public val uri: String? = null,
  public val cid: String? = null,
  public val `val`: String? = null,
  public val neg: Boolean? = null,
  public val cts: String? = null,
  public val exp: String? = null,
  public val sig: JsonElement? = null,
)

@Serializable
public data class SelfLabels(
  public val values: List<SelfLabel?>? = null,
)

@Serializable
public data class SelfLabel(
  public val `val`: String? = null,
)

@Serializable
public data class LabelValueDefinition(
  public val identifier: String? = null,
  public val severity: String? = null,
  public val blurs: String? = null,
  public val defaultSetting: String? = null,
  public val adultOnly: Boolean? = null,
  public val locales: List<LabelValueDefinitionStrings?>? = null,
)

@Serializable
public data class LabelValueDefinitionStrings(
  public val lang: String? = null,
  public val name: String? = null,
  public val description: String? = null,
)
