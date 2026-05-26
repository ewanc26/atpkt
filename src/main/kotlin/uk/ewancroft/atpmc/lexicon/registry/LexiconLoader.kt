package uk.ewancroft.atpmc.lexicon.registry

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.InputStream
import org.slf4j.LoggerFactory

/**
 * Utility to load and register AT Protocol Lexicon definitions.
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
            val id = schema["id"]?.toString()?.replace("\"", "")
            
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
