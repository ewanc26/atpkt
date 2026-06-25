package uk.ewancroft.atpkt.oauth

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.slf4j.LoggerFactory
import uk.ewancroft.atpkt.client.AtpHttpClient
import uk.ewancroft.atpkt.core.AtProtoClient
import uk.ewancroft.atpkt.crypto.CryptoUtil
import uk.ewancroft.atpkt.did.DidResolver
import uk.ewancroft.atpkt.oauth.dpop.DpopProof
import java.security.KeyPair

// ── OAuth 2.0 client (AT Protocol flavour) ─────────

/**
 * Core OAuth client for AT Protocol.
 * Handles metadata discovery, PAR (Pushed Authorization Requests),
 * token exchange, and session refresh, all bound with DPoP.
 *
 * Spec: https://atproto.com/specs/oauth
 */
class OAuthClient(
    private val atProtoClient: AtProtoClient = AtProtoClient(),
    private val didResolver: DidResolver = DidResolver()
) {
    private val logger = LoggerFactory.getLogger("atpkt:oauth")
    private val client = AtpHttpClient.client
    private val metadataResolver = MetadataResolver()

    /**
     * Data required to initiate an authorization request.
     */
    data class AuthorizationRequest(
        val url: String,
        val state: String,
        val codeVerifier: String,
        val dpopKeyPair: KeyPair,
        val authMetadata: AuthorizationServerMetadata,
        val clientId: String
    )

    /**
     * Resolves metadata and prepares a Pushed Authorization Request (PAR).
     *
     * @param identifier User's handle or DID.
     * @param clientId The client_id (e.g., a localhost URL for desktop apps).
     * @param redirectUri The redirect_uri registered for the client.
     * @param scope Requested scopes (default: "atproto").
     * @return AuthorizationRequest containing the URL to open in a browser and session state.
     */
    private data class ResolveHandleResponse(val did: String)

    suspend fun prepareAuthorization(
        identifier: String,
        clientId: String,
        redirectUri: String,
        scope: String = "atproto"
    ): Result<AuthorizationRequest> = runCatching {
        // 1. Resolve identity
        val did = if (identifier.startsWith("did:")) identifier else {
            atProtoClient.xrpcRequest("GET", "com.atproto.identity.resolveHandle?handle=$identifier", pdsUrl = "https://bsky.social")
                .map { uk.ewancroft.atpkt.xrpc.Xrpc.json.decodeFromString<ResolveHandleResponse>(it).did }
                .getOrThrow()
        }

        val didDoc = didResolver.resolve(did).getOrThrow()
        val pdsUrl = didDoc.service.find { it.type == "AtprotoPersonalDataServer" }?.serviceEndpoint
            ?: throw Exception("PDS not found for $did")

        // 2. Discover metadata
        val resourceMetadata = metadataResolver.resolveResourceServer(pdsUrl).getOrThrow()
        val authIssuer = resourceMetadata.authorizationServers?.firstOrNull() ?: resourceMetadata.issuer
        val authMetadata = metadataResolver.resolveAuthorizationServer(authIssuer).getOrThrow()

        val parEndpoint = authMetadata.parEndpoint ?: throw Exception("PAR not supported by $authIssuer")

        // 3. Prepare PKCE and DPoP
        val codeVerifier = PkceUtils.generateCodeVerifier()
        val codeChallenge = PkceUtils.generateCodeChallenge(codeVerifier)
        val dpopKeyPair = CryptoUtil.generateKeyPair()
        val state = java.util.UUID.randomUUID().toString()

        // 4. Submit PAR
        val parResponse = submitPar(
            parEndpoint,
            clientId,
            redirectUri,
            scope,
            state,
            codeChallenge,
            dpopKeyPair,
            identifier
        ).getOrThrow()

        val authUrl = "${authMetadata.authorizationEndpoint}?client_id=${clientId.encode()}&request_uri=${parResponse.requestUri.encode()}"

        AuthorizationRequest(
            url = authUrl,
            state = state,
            codeVerifier = codeVerifier,
            dpopKeyPair = dpopKeyPair,
            authMetadata = authMetadata,
            clientId = clientId
        )
    }

    /**
     * Exchanges an authorization code for tokens.
     */
    suspend fun exchangeCode(
        code: String,
        request: AuthorizationRequest
    ): Result<TokenResponse> = runCatching {
        val dpopProof = DpopProof.createProof(
            method = "POST",
            url = request.authMetadata.tokenEndpoint,
            privateKey = request.dpopKeyPair.private,
            publicKey = request.dpopKeyPair.public
        )

        val response = client.submitForm(
            url = request.authMetadata.tokenEndpoint,
            formParameters = parameters {
                append("grant_type", "authorization_code")
                append("code", code)
                append("client_id", request.clientId)
                append("code_verifier", request.codeVerifier)
            }
        ) {
            header("DPoP", dpopProof)
        }

        if (!response.status.isSuccess()) {
            throw Exception("Token exchange failed: ${response.status} - ${response.bodyAsText()}")
        }

        response.body<TokenResponse>()
    }

    private suspend fun submitPar(
        endpoint: String,
        clientId: String,
        redirectUri: String,
        scope: String,
        state: String,
        codeChallenge: String,
        dpopKeyPair: KeyPair,
        loginHint: String? = null
    ): Result<ParResponse> = runCatching {
        val dpopProof = DpopProof.createProof(
            method = "POST",
            url = endpoint,
            privateKey = dpopKeyPair.private,
            publicKey = dpopKeyPair.public
        )

        val response = client.submitForm(
            url = endpoint,
            formParameters = parameters {
                append("response_type", "code")
                append("client_id", clientId)
                append("redirect_uri", redirectUri)
                append("scope", scope)
                append("state", state)
                append("code_challenge", codeChallenge)
                append("code_challenge_method", PkceUtils.getChallengeMethod())
                loginHint?.let { append("login_hint", it) }
            }
        ) {
            header("DPoP", dpopProof)
        }

        if (response.status == HttpStatusCode.BadRequest || response.status == HttpStatusCode.Unauthorized) {
            val nonce = response.headers["DPoP-Nonce"]
            if (nonce != null) {
                // Retry with nonce
                return@runCatching retryParWithNonce(endpoint, clientId, redirectUri, scope, state, codeChallenge, dpopKeyPair, loginHint, nonce).getOrThrow()
            }
        }

        if (!response.status.isSuccess()) {
            throw Exception("PAR failed: ${response.status} - ${response.bodyAsText()}")
        }

        response.body<ParResponse>()
    }

    private suspend fun retryParWithNonce(
        endpoint: String,
        clientId: String,
        redirectUri: String,
        scope: String,
        state: String,
        codeChallenge: String,
        dpopKeyPair: KeyPair,
        loginHint: String?,
        nonce: String
    ): Result<ParResponse> = runCatching {
        val dpopProof = DpopProof.createProof(
            method = "POST",
            url = endpoint,
            privateKey = dpopKeyPair.private,
            publicKey = dpopKeyPair.public,
            nonce = nonce
        )

        val response = client.submitForm(
            url = endpoint,
            formParameters = parameters {
                append("response_type", "code")
                append("client_id", clientId)
                append("redirect_uri", redirectUri)
                append("scope", scope)
                append("state", state)
                append("code_challenge", codeChallenge)
                append("code_challenge_method", PkceUtils.getChallengeMethod())
                loginHint?.let { append("login_hint", it) }
            }
        ) {
            header("DPoP", dpopProof)
        }

        if (!response.status.isSuccess()) {
            throw Exception("PAR retry failed: ${response.status} - ${response.bodyAsText()}")
        }

        response.body<ParResponse>()
    }

    /**
     * Refreshes an OAuth session using a refresh token.
     */
    suspend fun refreshToken(
        refreshToken: String,
        clientId: String,
        tokenEndpoint: String,
        dpopKeyPair: KeyPair,
        authServerNonce: String? = null
    ): Result<TokenResponse> = runCatching {
        val dpopProof = DpopProof.createProof(
            method = "POST",
            url = tokenEndpoint,
            privateKey = dpopKeyPair.private,
            publicKey = dpopKeyPair.public,
            nonce = authServerNonce
        )

        val response = client.submitForm(
            url = tokenEndpoint,
            formParameters = parameters {
                append("grant_type", "refresh_token")
                append("refresh_token", refreshToken)
                append("client_id", clientId)
            }
        ) {
            header("DPoP", dpopProof)
        }

        if (response.status == HttpStatusCode.BadRequest || response.status == HttpStatusCode.Unauthorized) {
            val nonce = response.headers["DPoP-Nonce"]
            if (nonce != null && nonce != authServerNonce) {
                // Retry with new nonce
                return@runCatching refreshToken(refreshToken, clientId, tokenEndpoint, dpopKeyPair, nonce).getOrThrow()
            }
        }

        if (!response.status.isSuccess()) {
            throw Exception("Token refresh failed: ${response.status} - ${response.bodyAsText()}")
        }

        response.body<TokenResponse>()
    }

    private fun String.encode(): String = java.net.URLEncoder.encode(this, "UTF-8")
}
