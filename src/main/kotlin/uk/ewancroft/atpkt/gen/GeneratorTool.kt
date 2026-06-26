package uk.ewancroft.atpkt.gen

import uk.ewancroft.atpkt.lexicon.registry.LexiconLoader
import uk.ewancroft.atpkt.lexicon.registry.LexiconRegistry
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.extension
import kotlin.io.path.isDirectory

// ── Code generator CLI entry point ─────────────────

/**
 * CLI entry point for the code generator.
 * Walks the lexicon resource files, registers them, then generates
 * corresponding Kotlin data classes via KotlinPoet.
 */
object GeneratorTool {
    @JvmStatic
    fun main(args: Array<String>) {
        val loader = LexiconLoader
        val outputDir = File("src/main/kotlin")
        
        // Load all lexicons from resources
        val lexiconDir = Paths.get("src/main/resources/lexicons")
        if (Files.exists(lexiconDir)) {
            Files.walk(lexiconDir)
                .filter { it.extension == "json" }
                .forEach { path ->
                    val relativePath = lexiconDir.relativize(path).toString()
                    loader.loadFromResource("lexicons/$relativePath")
                }
        }
        
        println("Registered ${LexiconRegistry.getAllIds().size} lexicons.")
        LexiconRegistry.getAllIds().forEach { println("ID: $it") }
        
        LexiconRegistry.getAllIds().forEach { id ->
            try {
                val fileSpec = generator.generateKotlinClass(id)
                if (fileSpec != null) {
                    fileSpec.writeTo(outputDir)
                }
            } catch (e: Exception) {
                println("Failed to generate class for $id: ${e.message}")
            }
        }
    }
}
