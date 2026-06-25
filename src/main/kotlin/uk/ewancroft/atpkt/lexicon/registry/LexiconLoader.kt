package uk.ewancroft.atpkt.lexicon.registry

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.InputStream
import org.slf4j.LoggerFactory

// ── Lexicon file loader ────────────────────────────

/**
 * Utility to load and register AT Protocol Lexicon definitions from
 * JSON resource files bundled with the SDK.
 */
object LexiconLoader {
    private val logger = LoggerFactory.getLogger("atpmc-lexicon-loader")
    private val json = Json { ignoreUnknownKeys = true }

    fun loadFromResource(resourcePath: String) {
        val stream: InputStream? = LexiconLoader::class.java.classLoader.getResourceAsStream(resourcePath)
        
        if (stream == null) {
            logger.error("Lexicon file not found: $resourcePath")
            return
        }

        try {
            val content = stream.bufferedReader().use { it.readText() }
            val schema = json.decodeFromString<JsonObject>(content)
            
            // Extract the 'id' field from the lexicon schema
            val id = schema["id"]?.jsonPrimitive?.content
            
            if (id != null) {
                LexiconRegistry.register(id, schema)
                logger.info("Successfully registered lexicon: $id")
            } else {
                logger.error("Lexicon file $resourcePath missing 'id' field")
            }
        } catch (e: Exception) {
            logger.error("Failed to load lexicon: $resourcePath", e)
        }
    }
}
