package uk.ewancroft.atpmc.gen

import org.junit.jupiter.api.Test
import uk.ewancroft.atpmc.lexicon.registry.LexiconLoader
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LexiconGeneratorTest {

    @Test
    fun `test generate kotlin class`() {
        LexiconLoader.loadFromResource("lexicons/com.example.test.json")
        val fileSpec = LexiconGenerator.generateKotlinClass("com.example.test")
        
        assertNotNull(fileSpec)
        val kotlinCode = fileSpec.toString()
        
        assertTrue(kotlinCode.contains("data class Test("))
        assertTrue(kotlinCode.contains("val message: String"))
        println(kotlinCode)
    }
}
