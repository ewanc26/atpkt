package uk.ewancroft.atpmc.gen

import org.junit.jupiter.api.Test
import uk.ewancroft.atpmc.lexicon.registry.LexiconLoader
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UnionGeneratorTest {

    @Test
    fun `test generate union type`() {
        LexiconLoader.loadFromResource("lexicons/com.example.union.json")
        val fileSpec = LexiconGenerator.generateKotlinClass("com.example.union")
        
        assertNotNull(fileSpec)
        val kotlinCode = fileSpec.toString()
        
        // Check for sealed interface generation for the union field
        assertTrue(kotlinCode.contains("interface UnionFieldUnion"))
        assertTrue(kotlinCode.contains("sealed"))
        println(kotlinCode)
    }
}
