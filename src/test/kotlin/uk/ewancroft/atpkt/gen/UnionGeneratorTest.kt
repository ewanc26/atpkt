package uk.ewancroft.atpkt.gen

import org.junit.jupiter.api.Test
import uk.ewancroft.atpkt.lexicon.registry.LexiconLoader
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UnionGeneratorTest {

    @Test
    fun `test generate union type`() {
        LexiconLoader.loadFromResource("lexicons/com.example.union.json")
        val fileSpec = LexiconGenerator.generateKotlinClass("com.example.union")
        
        assertNotNull(fileSpec)
        val kotlinCode = fileSpec.toString()
        println("Generated Code:\n$kotlinCode")
        
        // The union name depends on parent class name (Union) and field name (unionField)
        assertTrue(kotlinCode.contains("interface UnionUnionFieldUnion"))
        assertTrue(kotlinCode.contains("sealed"))
    }
}
