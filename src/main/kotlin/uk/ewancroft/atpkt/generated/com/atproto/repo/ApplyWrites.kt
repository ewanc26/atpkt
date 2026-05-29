package uk.ewancroft.atpkt.generated.com.atproto.repo

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class ApplyWritesInput(
  public val unused: String? = null,
)

@Serializable
public data class ApplyWritesOutput(
  public val unused: String? = null,
)

@Serializable
public data class Create(
  public val collection: String? = null,
  public val rkey: String? = null,
  public val `value`: JsonElement? = null,
)

@Serializable
public data class Update(
  public val collection: String? = null,
  public val rkey: String? = null,
  public val `value`: JsonElement? = null,
)

@Serializable
public data class Delete(
  public val collection: String? = null,
  public val rkey: String? = null,
)

@Serializable
public data class CreateResult(
  public val uri: String? = null,
  public val cid: String? = null,
  public val validationStatus: String? = null,
)

@Serializable
public data class UpdateResult(
  public val uri: String? = null,
  public val cid: String? = null,
  public val validationStatus: String? = null,
)

@Serializable
public data class DeleteResult()
