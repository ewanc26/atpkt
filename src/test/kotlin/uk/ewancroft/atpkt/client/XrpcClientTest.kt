package uk.ewancroft.atpkt.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.http.content.TextContent
import io.ktor.utils.io.ByteReadChannel
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable

@Serializable
private data class QueryResponse(val success: Boolean)

@Serializable
private data class ProcedureRequest(val message: String)

@Serializable
private data class ProcedureResponse(val result: String)

class XrpcClientTest : DescribeSpec({
    describe("XrpcClient") {
        it("executes a query and deserialises the response") {
            val httpClient = HttpClient(MockEngine { request ->
                request.url.toString() shouldBe "https://example.com/xrpc/com.atproto.test.query"
                request.method shouldBe HttpMethod.Get
                request.headers[HttpHeaders.Authorization] shouldBe "Bearer access-token"

                respond(
                    content = ByteReadChannel("""{"success":true}"""),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            })
            val client = XrpcClient("https://example.com", httpClient)

            val response = client.query(
                nsid = "com.atproto.test.query",
                params = null,
                responseSerializer = QueryResponse.serializer(),
                accessJwt = "access-token"
            )

            response shouldBe QueryResponse(success = true)
        }

        it("executes a procedure with JSON request bodies") {
            val httpClient = HttpClient(MockEngine { request ->
                request.url.toString() shouldBe "https://example.com/xrpc/com.atproto.test.create"
                request.method shouldBe HttpMethod.Post
                request.headers[HttpHeaders.Authorization] shouldBe "Bearer access-token"

                val body = request.body as TextContent
                body.text shouldBe """{"message":"hello"}"""

                respond(
                    content = ByteReadChannel("""{"result":"created"}"""),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            })
            val client = XrpcClient("https://example.com", httpClient)

            val response = client.procedure(
                nsid = "com.atproto.test.create",
                body = ProcedureRequest(message = "hello"),
                requestSerializer = ProcedureRequest.serializer(),
                responseSerializer = ProcedureResponse.serializer(),
                accessJwt = "access-token"
            )

            response shouldBe ProcedureResponse(result = "created")
        }

        it("surfaces XRPC errors from non-success responses") {
            val httpClient = HttpClient(MockEngine { _ ->
                respond(
                    content = ByteReadChannel("""{"error":"InvalidRequest","message":"The request was invalid"}"""),
                    status = HttpStatusCode.BadRequest,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            })
            val client = XrpcClient("https://example.com", httpClient)

            val exception = shouldThrow<Exception> {
                client.query(
                    nsid = "com.atproto.test.error",
                    params = null,
                    responseSerializer = QueryResponse.serializer()
                )
            }

            exception.message shouldBe "XRPC Error: InvalidRequest - The request was invalid"
        }
    }
})
