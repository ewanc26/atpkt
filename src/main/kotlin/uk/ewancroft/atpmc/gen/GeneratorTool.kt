package uk.ewancroft.atpmc.gen

import uk.ewancroft.atpmc.lexicon.registry.LexiconLoader
import uk.ewancroft.atpmc.lexicon.registry.LexiconRegistry
import java.io.File

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
        
        // Load lexicons and generate classes
        val lexicons = listOf("lexicons/com.example.test.json")
        lexicons.forEach { path ->
            loader.loadFromResource(path)
        }
        
        LexiconRegistry.getAllIds().forEach { id ->
            val fileSpec = generator.generateKotlinClass(id)
            if (fileSpec != null) {
                fileSpec.writeTo(outputDir)
            }
        }
    }
}
