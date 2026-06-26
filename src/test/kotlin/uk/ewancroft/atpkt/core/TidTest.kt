package uk.ewancroft.atpkt.core

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.Instant

class TidTest : DescribeSpec({
    describe("Tid") {
        it("generates valid, lexicographically sortable identifiers") {
            val instant = Instant.ofEpochMilli(1_700_000_000_000)
            val first = Tid.generate(instant)
            val second = Tid.generate(instant)

            Tid.validate(first) shouldBe true
            Tid.validate(second) shouldBe true
            first.length shouldBe 13
            second.length shouldBe 13
            (first < second) shouldBe true
        }

        it("rejects malformed identifiers") {
            Tid.validate("not-a-tid") shouldBe false
        }
    }
})
