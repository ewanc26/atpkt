package uk.ewancroft.atpkt.lexicon

import kotlinx.serialization.Serializable

// ── Lexicon schema models ──────────────────────────

/**
 * A parsed AT Protocol Lexicon schema.
 * Spec: https://atproto.com/specs/lexicon
 *
 * Each LexiconSchema describes one namespace ID (e.g. app.bsky.feed.post)
 * and its set of definitions (records, queries, procedures, objects).
 */
@Serializable
data class LexiconSchema(
    val lexicon: Int,
    val id: String,
    val revision: Int? = null,
    val description: String? = null,
    val defs: Map<String, LexiconDefinition>
)

/**
 * A single definition within a Lexicon schema.
 * The `type` field determines whether this is a record, query, procedure,
 * object, subscription, etc.
 */
@Serializable
data class LexiconDefinition(
    val type: String,
    val description: String? = null,
    val properties: Map<String, LexiconProperty>? = null,
    val required: List<String>? = null,
    val parameters: LexiconParameters? = null,
    val input: LexiconBody? = null,
    val output: LexiconBody? = null
)

/**
 * A typed property within a Lexicon definition.
 * Supports cross-references via `ref` and type constraints via format/length.
 */
@Serializable
data class LexiconProperty(
    val type: String,
    val description: String? = null,
    val format: String? = null,
    val maxLength: Int? = null,
    val minLength: Int? = null,
    val ref: String? = null
)

/**
 * Parameter definitions for query/procedure inputs.
 */
@Serializable
data class LexiconParameters(
    val properties: Map<String, LexiconProperty>
)

/**
 * Input/output body definition for a query or procedure.
 * Specifies encoding and an optional schema for the body.
 */
@Serializable
data class LexiconBody(
    val encoding: String,
    val schema: LexiconProperty? = null
)
