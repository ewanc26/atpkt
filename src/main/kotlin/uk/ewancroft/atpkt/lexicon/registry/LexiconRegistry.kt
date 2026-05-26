package uk.ewancroft.atpkt.lexicon.registry

import kotlinx.serialization.json.JsonObject
import java.util.concurrent.ConcurrentHashMap

/**
 * Registry for AT Protocol Lexicon definitions.
 * Provides a centralized lookup for schema validation and mapping.
 */
object LexiconRegistry {
    private val schemas = ConcurrentHashMap<String, JsonObject>()

    fun register(id: String, schema: JsonObject) {
        schemas[id] = schema
    }

    fun get(id: String): JsonObject? = schemas[id]

    fun contains(id: String): Boolean = schemas.containsKey(id)

    fun getAllIds(): Set<String> = schemas.keys
}
