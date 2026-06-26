package uk.ewancroft.atpkt.gen

import org.junit.jupiter.api.Test
import uk.ewancroft.atpkt.lexicon.registry.LexiconLoader
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

// Tests for union type generation from Lexicon schemas
class UnionGeneratorTest {

    @Test
    fun `test generate union type`() {
        LexiconLoader.loadFromResource("lexicons/com.example.union.json")
        val fileSpec = LexiconGenerator.generateKotlinClass("com.example.union")
        
        assertNotNull(fileSpec)
        val kotlinCode = fileSpec.toString()
        println("Generated Code:\n$kotlinCode")

        assertTrue(kotlinCode.contains("package uk.ewancroft.atpkt.generated.com.example"))
        assertTrue(kotlinCode.contains("data class Union"))
        assertTrue(kotlinCode.contains("data class TypeA"))
        assertTrue(kotlinCode.contains("data class TypeB"))
    }
}
