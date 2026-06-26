package uk.ewancroft.atpkt.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.xrpc.Xrpc
import java.util.UUID

class RecordManagerTest : DescribeSpec({
    describe("RecordManager") {
        it("creates records through the session client") {
            val playerUuid = UUID.randomUUID()
            val sessionManager = mockk<AtProtoSessionManager>()
            val client = mockk<AtProtoClient>()
            val manager = RecordManager(sessionManager)
            val session = AtProtoSessionManager.PlayerSession(
                did = "did:plc:record-test",
                handle = "user.example",
                pdsUrl = "https://pds.example",
                accessJwt = "access-jwt",
                refreshJwt = "refresh-jwt"
            )
            val record = parseRecord("""{"text":"hello"}""")
            val request = RecordManager.CreateRecordRequest(
                repo = session.did,
                collection = "app.bsky.feed.post",
                record = record,
                validate = true
            )
            val expectedBody = Xrpc.json.encodeToString(RecordManager.CreateRecordRequest.serializer(), request)

            every { sessionManager.client } returns client
            coEvery { sessionManager.getSession(playerUuid) } returns Result.success(session)
            coEvery {
                client.xrpcRequest(
                    method = "POST",
                    endpoint = "com.atproto.repo.createRecord",
                    accessJwt = "access-jwt",
                    pdsUrl = "https://pds.example",
                    body = expectedBody
                )
            } returns Result.success("""{"uri":"at://did:plc:record-test/app.bsky.feed.post/3k","cid":"bafyrecord"}""")

            val result = runBlocking {
                manager.createRecord(
                    playerUuid = playerUuid,
                    collection = "app.bsky.feed.post",
                    record = record
                )
            }.getOrThrow()

            result shouldBe RecordManager.StrongRef(
                uri = "at://did:plc:record-test/app.bsky.feed.post/3k",
                cid = "bafyrecord"
            )
        }

        it("fetches records through the session client") {
            val playerUuid = UUID.randomUUID()
            val sessionManager = mockk<AtProtoSessionManager>()
            val client = mockk<AtProtoClient>()
            val manager = RecordManager(sessionManager)
            val session = AtProtoSessionManager.PlayerSession(
                did = "did:plc:record-test",
                handle = "user.example",
                pdsUrl = "https://pds.example",
                accessJwt = "access-jwt",
                refreshJwt = "refresh-jwt"
            )
            val record = parseRecord("""{"text":"hello"}""")

            every { sessionManager.client } returns client
            coEvery { sessionManager.getSession(playerUuid) } returns Result.success(session)
            coEvery {
                client.xrpcRequest(
                    method = "GET",
                    endpoint = "com.atproto.repo.getRecord?repo=did:plc:record-test&collection=app.bsky.feed.post&rkey=3k",
                    accessJwt = "access-jwt",
                    pdsUrl = "https://pds.example"
                )
            } returns Result.success("""{"uri":"at://did:plc:record-test/app.bsky.feed.post/3k","cid":"bafyrecord","value":{"text":"hello"}}""")

            val result = runBlocking {
                manager.getRecord(
                    playerUuid = playerUuid,
                    collection = "app.bsky.feed.post",
                    rkey = "3k"
                )
            }.getOrThrow()

            result.uri shouldBe "at://did:plc:record-test/app.bsky.feed.post/3k"
            result.cid shouldBe "bafyrecord"
            result.value shouldBe record
        }

        it("returns a failure when a session cannot be found") {
            val sessionManager = mockk<AtProtoSessionManager>()
            val manager = RecordManager(sessionManager)
            val playerUuid = UUID.randomUUID()

            coEvery { sessionManager.getSession(playerUuid) } returns Result.failure(Exception("No session found"))

            val result = runBlocking {
                manager.createRecord(
                    playerUuid = playerUuid,
                    collection = "app.bsky.feed.post",
                    record = parseRecord("""{"text":"hello"}""")
                )
            }

            result.isFailure shouldBe true
            result.exceptionOrNull()?.message shouldBe "No session found"
        }
    }
})

private fun parseRecord(json: String): JsonElement = Json.parseToJsonElement(json)
