package uk.ewancroft.atpkt.client

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.mockk
import uk.ewancroft.atpkt.agent.Agent
import uk.ewancroft.atpkt.core.AtProtoSessionManager

class NamespaceTest : DescribeSpec({
    val mockSessionManager = mockk<AtProtoSessionManager>(relaxed = true)
    val agent = Agent(mockSessionManager)

    describe("ChatBskyNS") {
        it("can be instantiated with a mock Agent") {
            val ns = ChatBskyNS(agent)
            ns shouldNotBe null
        }

        it("exposes sub-namespaces") {
            val ns = ChatBskyNS(agent)
            ns.actor shouldNotBe null
            ns.convo shouldNotBe null
            ns.group shouldNotBe null
            ns.moderation shouldNotBe null
        }
    }

    describe("ToolsOzoneNS") {
        it("can be instantiated with a mock Agent") {
            val ns = ToolsOzoneNS(agent)
            ns shouldNotBe null
        }

        it("exposes sub-namespaces") {
            val ns = ToolsOzoneNS(agent)
            ns.communication shouldNotBe null
            ns.hosting shouldNotBe null
            ns.moderation shouldNotBe null
            ns.queue shouldNotBe null
            ns.report shouldNotBe null
            ns.safelink shouldNotBe null
            ns.server shouldNotBe null
            ns.set shouldNotBe null
            ns.setting shouldNotBe null
            ns.signature shouldNotBe null
            ns.team shouldNotBe null
            ns.verification shouldNotBe null
        }

        it("allows chained access through tools.ozone.moderation") {
            val toolsNS = ToolsNS(agent)
            toolsNS.ozone.moderation shouldNotBe null
        }
    }

    describe("ToolsNS") {
        it("can be instantiated with a mock Agent") {
            val ns = ToolsNS(agent)
            ns shouldNotBe null
        }

        it("exposes the ozone sub-namespace") {
            val ns = ToolsNS(agent)
            ns.ozone shouldNotBe null
            ns.ozone.shouldBeInstanceOf<ToolsOzoneNS>()
        }
    }

    describe("Agent") {
        it("exposes chat and tools properties") {
            agent.chat shouldNotBe null
            agent.tools shouldNotBe null
        }

        it("chat is a ChatBskyNS instance") {
            agent.chat.shouldBeInstanceOf<ChatBskyNS>()
        }

        it("tools is a ToolsNS instance") {
            agent.tools.shouldBeInstanceOf<ToolsNS>()
        }
    }
})
