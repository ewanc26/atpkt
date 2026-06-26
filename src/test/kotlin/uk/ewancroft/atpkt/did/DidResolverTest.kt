package uk.ewancroft.atpkt.did

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import uk.ewancroft.atpkt.client.AtpHttpClient
import java.util.concurrent.atomic.AtomicInteger

class DidResolverTest : DescribeSpec({
    describe("DidResolver") {
        it("resolves did:plc documents and caches the result") {
            val requestCount = AtomicInteger(0)
            val httpClient = HttpClient(MockEngine { request ->
                requestCount.incrementAndGet()
                request.url.toString() shouldBe "https://plc.example/plc123"

                respond(
                    content = ByteReadChannel(
                        """
                        {
                          "id": "did:plc:plc123",
                          "service": [
                            {
                              "id": "#pds",
                              "type": "AtprotoPersonalDataServer",
                              "serviceEndpoint": "https://pds.example"
                            }
                          ]
                        }
                        """.trimIndent()
                    ),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            })

            mockkObject(AtpHttpClient)
            try {
                every { AtpHttpClient.client } returns httpClient

                val resolver = DidResolver(plcUrl = "https://plc.example", cacheTtlSeconds = 3600)
                val did = "did:plc:plc123"

                val first = resolver.resolve(did).getOrThrow()
                val second = resolver.resolve(did).getOrThrow()

                first shouldBe second
                first.service.single().serviceEndpoint shouldBe "https://pds.example"
                requestCount.get() shouldBe 1
            } finally {
                unmockkObject(AtpHttpClient)
            }
        }

        it("resolves did:web documents from the well-known endpoint") {
            val httpClient = HttpClient(MockEngine { request ->
                request.url.toString() shouldBe "https://example.com/.well-known/did.json"

                respond(
                    content = ByteReadChannel(
                        """
                        {
                          "id": "did:web:example.com",
                          "service": [
                            {
                              "id": "#pds",
                              "type": "AtprotoPersonalDataServer",
                              "serviceEndpoint": "https://pds.example"
                            }
                          ]
                        }
                        """.trimIndent()
                    ),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            })

            mockkObject(AtpHttpClient)
            try {
                every { AtpHttpClient.client } returns httpClient

                val resolver = DidResolver()
                val document = resolver.resolve("did:web:example.com").getOrThrow()

                document.id shouldBe "did:web:example.com"
                document.service.single().serviceEndpoint shouldBe "https://pds.example"
            } finally {
                unmockkObject(AtpHttpClient)
            }
        }

        it("fails for unsupported DID methods") {
            val httpClient = HttpClient(MockEngine { _ ->
                error("Unexpected network call")
            })

            mockkObject(AtpHttpClient)
            try {
                every { AtpHttpClient.client } returns httpClient

                val resolver = DidResolver()
                val result = resolver.resolve("did:key:z123")

                result.isFailure shouldBe true
                result.exceptionOrNull()?.message shouldBe "Unsupported DID method: did:key:z123"
            } finally {
                unmockkObject(AtpHttpClient)
            }
        }
    }
})
