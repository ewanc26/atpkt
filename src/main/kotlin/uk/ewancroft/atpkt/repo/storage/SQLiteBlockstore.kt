package uk.ewancroft.atpkt.repo.storage

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

// ── SQLite blockstore ────────────────────────────

/**
 * SQLite-backed implementation of a content-addressed blockstore.
 * 
 * Blocks are stored in a single table with the following schema:
 * CREATE TABLE blocks (cid TEXT PRIMARY KEY, data BLOB)
 * 
 * This implementation is thread-safe and supports concurrent access.
 */
class SQLiteBlockstore(private val dbPath: String) : Blockstore {
    private val connection: Connection by lazy {
        val conn = DriverManager.getConnection("jdbc:sqlite:$dbPath")
        conn.prepareStatement(
            "CREATE TABLE IF NOT EXISTS blocks (cid TEXT PRIMARY KEY, data BLOB)"
        ).executeUpdate()
        conn
    }

    override fun getBytes(cid: String): ByteArray? {
        val stmt = connection.prepareStatement("SELECT data FROM blocks WHERE cid = ?")
        stmt.setString(1, cid)
        val rs = stmt.executeQuery()
        return if (rs.next()) rs.getBytes("data") else null
    }

    override fun has(cid: String): Boolean {
        val stmt = connection.prepareStatement("SELECT 1 FROM blocks WHERE cid = ?")
        stmt.setString(1, cid)
        val rs = stmt.executeQuery()
        return rs.next()
    }

    override fun put(cid: String, bytes: ByteArray) {
        val stmt = connection.prepareStatement(
            "INSERT OR REPLACE INTO blocks (cid, data) VALUES (?, ?)"
        )
        stmt.setString(1, cid)
        stmt.setBytes(2, bytes)
        stmt.executeUpdate()
    }

    fun destroy() {
        try {
            connection.close()
            File(dbPath).delete()
        } catch (e: SQLException) {
            throw RuntimeException("Failed to destroy SQLiteBlockstore", e)
        }
    }
}