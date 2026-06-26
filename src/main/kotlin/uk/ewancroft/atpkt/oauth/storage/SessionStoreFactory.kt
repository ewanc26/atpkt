package uk.ewancroft.atpkt.oauth.storage

import java.nio.file.Path
import javax.crypto.SecretKey

/**
 * Convenience factory for common session store configurations.
 */
object SessionStoreFactory {
    fun inMemory(): SessionStore = InMemorySessionStore()

    fun file(dir: Path): SessionStore = FileSessionStore(dir)

    fun encrypted(delegate: SessionStore, key: SecretKey): SessionStore = EncryptedSessionStore(delegate, key)
}
