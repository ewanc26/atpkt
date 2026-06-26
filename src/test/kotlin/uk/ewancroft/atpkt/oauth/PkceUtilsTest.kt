package uk.ewancroft.atpkt.oauth

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class PkceUtilsTest : DescribeSpec({
    describe("PkceUtils") {
        it("generates a URL-safe code verifier") {
            val verifier = PkceUtils.generateCodeVerifier()
            val allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"

            verifier.length shouldBe 43
            verifier.all { it in allowedCharacters } shouldBe true
        }

        it("generates a different verifier each time") {
            val first = PkceUtils.generateCodeVerifier()
            val second = PkceUtils.generateCodeVerifier()

            first shouldNotBe second
        }

        it("computes the RFC 7636 example challenge") {
            val verifier = "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"
            val challenge = PkceUtils.generateCodeChallenge(verifier)

            challenge shouldBe "E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM"
            PkceUtils.getChallengeMethod() shouldBe "S256"
        }
    }
})
