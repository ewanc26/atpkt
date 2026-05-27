package uk.ewancroft.atpkt.generated

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Profile(
  public val displayName: String,
  public val description: String,
  public val pronouns: String,
  public val website: String,
  public val avatar: JsonElement,
  public val banner: JsonElement,
  public val labels: LabelsUnion,
  public val joinedViaStarterPack: Com.atproto.repo.strongRef,
  public val pinnedPost: Com.atproto.repo.strongRef,
  public val createdAt: String,
)

@Serializable
public sealed interface LabelsUnion

@Serializable
public sealed interface LabelsUnion
