package uk.ewancroft.atpmc.lexicon.registry

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LexiconLoaderTest {

    @Test
    fun `test load and register lexicon`() {
        LexiconLoader.loadFromResource("lexicons/com.example.test.json")
        
        val schema = LexiconRegistry.get("com.example.test")
        assertNotNull(schema, "Schema should be registered")
        assertTrue(LexiconRegistry.contains("com.example.test"))
    }
}
