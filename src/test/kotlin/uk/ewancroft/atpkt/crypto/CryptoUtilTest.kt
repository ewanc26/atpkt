package uk.ewancroft.atpkt.crypto

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.Base64

class CryptoUtilTest : DescribeSpec({
    describe("CryptoUtil") {
        it("generates a usable EC key pair") {
            val keyPair = CryptoUtil.generateKeyPair()
            val payload = "hello world".encodeToByteArray()
            val signature = CryptoUtil.signCommit(payload, keyPair.private)

            keyPair.public.algorithm shouldBe "EC"
            keyPair.private.algorithm shouldBe "EC"
            CryptoUtil.verifyCommit(payload, signature, keyPair.public) shouldBe true
        }

        it("exports a P-256 public key as a JWK") {
            val keyPair = CryptoUtil.generateKeyPair()
            val jwk = CryptoUtil.getJwk(keyPair.public)
            val decoder = Base64.getUrlDecoder()

            jwk["kty"] shouldBe "EC"
            jwk["crv"] shouldBe "P-256"
            decoder.decode(jwk.getValue("x")).size shouldBe 32
            decoder.decode(jwk.getValue("y")).size shouldBe 32
        }
    }
})
