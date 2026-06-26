package uk.ewancroft.atpkt.agent

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import uk.ewancroft.atpkt.agent.AtprotoNamespace
import uk.ewancroft.atpkt.core.AtProtoClient
import uk.ewancroft.atpkt.core.AtProtoSessionManager
import uk.ewancroft.atpkt.did.DidResolver
import uk.ewancroft.atpkt.oauth.OAuthSessionManager

class AgentTest : DescribeSpec({
    describe("Agent") {
        it("resolves the PDS service endpoint from a DID document") {
            val sessionManager = mockk<AtProtoSessionManager>(relaxed = true)
            val didResolver = mockk<DidResolver>()
            val agent = Agent(sessionManager, didResolver)
            val did = "did:plc:agent-test"
            val didDocument = DidResolver.DidDocument(
                id = did,
                service = listOf(
                    DidResolver.DidService(
                        id = "#pds",
                        type = "AtprotoPersonalDataServer",
                        serviceEndpoint = "https://pds.example"
                    )
                )
            )

            coEvery { didResolver.resolve(did) } returns Result.success(didDocument)

            val pdsUrl = runBlocking { agent.resolvePds(did) }.getOrThrow()

            pdsUrl shouldBe "https://pds.example"
        }
    }

    describe("AtpAgent") {
        it("exposes nested namespaces") {
            val client = mockk<AtProtoClient>(relaxed = true)
            val sessions = mockk<OAuthSessionManager>(relaxed = true)
            val agent = AtpAgent(client = client, sessions = sessions)

            agent.com.atproto.shouldBeInstanceOf<AtprotoNamespace>()
            agent.app.bsky shouldNotBe null
        }
    }
})
