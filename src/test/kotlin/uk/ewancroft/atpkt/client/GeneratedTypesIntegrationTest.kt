package uk.ewancroft.atpkt.client

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import uk.ewancroft.atpkt.agent.Agent
import uk.ewancroft.atpkt.core.AtProtoClient
import uk.ewancroft.atpkt.core.AtProtoSessionManager
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewDetailed
import uk.ewancroft.atpkt.generated.app.bsky.feed.FeedViewPost
import uk.ewancroft.atpkt.generated.app.bsky.feed.GetFeedOutput
import uk.ewancroft.atpkt.generated.app.bsky.feed.Post
import uk.ewancroft.atpkt.generated.com.atproto.repo.CreateRecordOutput
import uk.ewancroft.atpkt.xrpc.Xrpc

class GeneratedTypesIntegrationTest : DescribeSpec({
    describe("generated lexicon types") {
        it("should round-trip record and response models") {
            val post = Post(
                text = "Hello, world",
                createdAt = "2026-01-01T00:00:00Z"
            )
            val postJson = Xrpc.json.encodeToString(Post.serializer(), post)
            val postRoundTrip = Xrpc.json.decodeFromString(Post.serializer(), postJson)
            postRoundTrip shouldBe post

            val profile = ProfileViewDetailed(
                did = "did:plc:123",
                handle = "ewan.test",
                displayName = "Ewan"
            )
            val profileJson = Xrpc.json.encodeToString(ProfileViewDetailed.serializer(), profile)
            val profileRoundTrip = Xrpc.json.decodeFromString(ProfileViewDetailed.serializer(), profileJson)
            profileRoundTrip shouldBe profile

            val feedResponse = GetFeedOutput(feed = listOf(FeedViewPost()))
            val feedJson = Xrpc.json.encodeToString(GetFeedOutput.serializer(), feedResponse)
            val feedRoundTrip = Xrpc.json.decodeFromString(GetFeedOutput.serializer(), feedJson)
            feedRoundTrip shouldBe feedResponse
        }

        it("should return generated profile and post creation responses from the namespaces") {
            val client = mockk<AtProtoClient>()
            val sessionManager = mockk<AtProtoSessionManager>()
            every { sessionManager.client } returns client

            val agent = Agent(sessionManager)

            val profile = ProfileViewDetailed(
                did = "did:plc:123",
                handle = "ewan.test",
                displayName = "Ewan"
            )
            coEvery {
                client.xrpcRequest(
                    method = "GET",
                    endpoint = "app.bsky.actor.getProfile?actor=did:plc:123",
                    accessJwt = null,
                    pdsUrl = "https://bsky.social",
                    body = null
                )
            } returns Result.success(Xrpc.json.encodeToString(ProfileViewDetailed.serializer(), profile))

            val created = CreateRecordOutput(
                uri = "at://did:plc:123/app.bsky.feed.post/3j1",
                cid = "bafyreigh",
                validationStatus = "valid"
            )
            coEvery {
                client.xrpcRequest(
                    method = "POST",
                    endpoint = "com.atproto.repo.createRecord",
                    accessJwt = null,
                    pdsUrl = "https://bsky.social",
                    body = any()
                )
            } returns Result.success(Xrpc.json.encodeToString(CreateRecordOutput.serializer(), created))

            val profileResult = shouldNotThrowAny {
                runBlocking { agent.app.actor.getProfile("did:plc:123").getOrThrow() }
            }
            profileResult.handle shouldBe "ewan.test"

            val createdResult = shouldNotThrowAny {
                runBlocking { agent.app.feed.createPost("did:plc:123", "Hello, world").getOrThrow() }
            }
            createdResult.uri shouldBe created.uri
            createdResult.cid shouldBe created.cid
        }
    }
})
