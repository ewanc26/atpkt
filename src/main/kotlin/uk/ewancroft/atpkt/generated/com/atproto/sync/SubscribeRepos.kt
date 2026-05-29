package uk.ewancroft.atpkt.generated.com.atproto.sync

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
public data class Commit(
  public val seq: Long? = null,
  public val rebase: Boolean? = null,
  public val tooBig: Boolean? = null,
  public val repo: String? = null,
  public val commit: JsonElement? = null,
  public val rev: String? = null,
  public val since: String? = null,
  public val blocks: JsonElement? = null,
  public val ops: List<RepoOp?>? = null,
  public val blobs: List<JsonElement?>? = null,
  public val prevData: JsonElement? = null,
  public val time: String? = null,
)

@Serializable
public data class Sync(
  public val seq: Long? = null,
  public val did: String? = null,
  public val blocks: JsonElement? = null,
  public val rev: String? = null,
  public val time: String? = null,
)

@Serializable
public data class Identity(
  public val seq: Long? = null,
  public val did: String? = null,
  public val time: String? = null,
  public val handle: String? = null,
)

@Serializable
public data class Account(
  public val seq: Long? = null,
  public val did: String? = null,
  public val time: String? = null,
  public val active: Boolean? = null,
  public val status: String? = null,
)

@Serializable
public data class Info(
  public val name: String? = null,
  public val message: String? = null,
)

@Serializable
public data class RepoOp(
  public val action: String? = null,
  public val path: String? = null,
  public val cid: JsonElement? = null,
  public val prev: JsonElement? = null,
)
