package uk.ewancroft.atpkt.client

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.xrpc.Xrpc

@Serializable
data class MockResponse(val success: Boolean)

class XrpcClientTest : DescribeSpec({
    describe("XrpcClient") {
        it("should successfully execute a query and deserialize the response") {
            val mockEngine = MockEngine { request ->
                respond(
                    content = ByteReadChannel("""{"success": true}"""),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val httpClient = HttpClient(mockEngine)
            val client = XrpcClient("https://example.com", httpClient)
            
            val response = client.query(
                "com.atproto.test.query",
                null,
                MockResponse.serializer()
            )
            
            response.success shouldBe true
        }

        it("should throw an exception on XRPC error") {
            val mockEngine = MockEngine { request ->
                respond(
                    content = ByteReadChannel("""{"error": "InvalidRequest", "message": "The request was invalid"}"""),
                    status = HttpStatusCode.BadRequest,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val httpClient = HttpClient(mockEngine)
            val client = XrpcClient("https://example.com", httpClient)
            
            val exception = io.kotest.assertions.throwables.shouldThrow<Exception> {
                client.query("com.atproto.test.error", null, MockResponse.serializer())
            }
            
            exception.message shouldBe "XRPC Error: InvalidRequest - The request was invalid"
        }
    }
})
