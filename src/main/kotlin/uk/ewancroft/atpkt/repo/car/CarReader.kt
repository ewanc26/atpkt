package uk.ewancroft.atpkt.repo.car

import java.io.InputStream
import java.nio.ByteBuffer

// ── CAR (Content Addressable Archive) reader ───────

/**
 * Basic CAR (Content Addressable Archive) reader utility.
 * Used for parsing repository state archives as defined in the AT Protocol.
 *
 * CAR frames consist of a 4-byte length prefix followed by the frame data,
 * which contains a CID and the block bytes. This reader handles sequential
 * frame extraction from an InputStream.
 * Spec: https://atproto.com/specs/repository#car-format
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
