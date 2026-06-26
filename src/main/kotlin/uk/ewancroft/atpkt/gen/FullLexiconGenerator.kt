package uk.ewancroft.atpkt.gen

import com.squareup.kotlinpoet.*
import uk.ewancroft.atpkt.lexicon.*
import java.io.File

// ── Full lexicon code generator ────────────────────

/**
 * Extended LexiconGenerator capable of producing the full API surface area.
 * Still a work-in-progress: the namespace layer and model generation methods
 * are stubs awaiting the full KotlinPoet wiring.
 */
class FullLexiconGenerator(private val outputDir: File) {
    
    fun generateAll(schemas: List<LexiconSchema>) {
        schemas.forEach { schema ->
            generateNamespaceLayer(schema)
            generateModels(schema)
        }
    }

    private fun generateNamespaceLayer(schema: LexiconSchema) {
        // Generates the 'agent.app.bsky.feed' style extensions
        // Each query/procedure becomes a method in the corresponding namespace class
    }

    private fun generateModels(schema: LexiconSchema) {
        // Generates the Request, Response, and Record models
    }
}
