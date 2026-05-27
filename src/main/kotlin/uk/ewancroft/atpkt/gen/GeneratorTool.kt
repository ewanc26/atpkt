package uk.ewancroft.atpkt.gen

import uk.ewancroft.atpkt.lexicon.registry.LexiconLoader
import uk.ewancroft.atpkt.lexicon.registry.LexiconRegistry
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.extension
import kotlin.io.path.isDirectory

/**
 * CLI entry point for the code generator.
 */
object GeneratorTool {
    @JvmStatic
    fun main(args: Array<String>) {
        val loader = LexiconLoader
        val generator = LexiconGenerator
        
        // Define directory to store generated classes
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
