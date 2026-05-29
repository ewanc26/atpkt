package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Publication(
  public val basicTheme: Site.standard.theme.basic,
  public val description: String,
  public val icon: JsonElement,
  public val labels: LabelsUnion,
  public val name: String,
  public val preferences: Preferences,
  public val url: String,
)

@Serializable
public sealed interface LabelsUnion

@Serializable
public sealed interface LabelsUnion
