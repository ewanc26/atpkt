package uk.ewancroft.atpkt.repo.car

import java.io.InputStream
import java.nio.ByteBuffer

/**
 * Basic CAR (Content Addressable Archive) reader utility.
 * Used for parsing repository state archives as defined in the AT Protocol.
 */
class CarReader(private val inputStream: InputStream) {

    /**
     * Reads a single CAR frame from the stream.
     * AT Protocol CAR archives consist of a sequence of frames, 
     * each containing a header (length) followed by a CID and block data.
     */
    fun readNextFrame(): ByteArray? {
        val lengthBytes = inputStream.readNBytes(4)
        if (lengthBytes.isEmpty()) return null
        
        val length = ByteBuffer.wrap(lengthBytes).int
        return inputStream.readNBytes(length)
    }
}
