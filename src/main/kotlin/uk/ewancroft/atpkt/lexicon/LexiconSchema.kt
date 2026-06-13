package uk.ewancroft.atpkt.lexicon

import kotlinx.serialization.Serializable

@Serializable
data class LexiconSchema(
    val lexicon: Int,
    val id: String,
    val revision: Int? = null,
    val description: String? = null,
    val defs: Map<String, LexiconDefinition>
)

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

@Serializable
data class LexiconProperty(
    val type: String,
    val description: String? = null,
    val format: String? = null,
    val maxLength: Int? = null,
    val minLength: Int? = null,
    val ref: String? = null
)

@Serializable
data class LexiconParameters(
    val properties: Map<String, LexiconProperty>
)

@Serializable
data class LexiconBody(
    val encoding: String,
    val schema: LexiconProperty? = null
)
