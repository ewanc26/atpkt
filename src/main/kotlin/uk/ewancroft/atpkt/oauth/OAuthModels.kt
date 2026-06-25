package uk.ewancroft.atpkt.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ── OAuth wire-format models ───────────────────────

/**
 * Metadata describing an OAuth 2.0 Authorization Server.
 * Fetched from the server's .well-known endpoint. Maps to RFC 8414.
 */
@Serializable
data class AuthorizationServerMetadata(
    val issuer: String,
    @SerialName("authorization_endpoint") val authorizationEndpoint: String,
    @SerialName("token_endpoint") val tokenEndpoint: String,
    @SerialName("jwks_uri") val jwksUri: String? = null,
    // AT Protocol mandates PAR support
    @SerialName("pushed_authorization_request_endpoint") val parEndpoint: String? = null,
    @SerialName("require_pushed_authorization_requests") val requirePar: Boolean = false,
    @SerialName("response_types_supported") val responseTypesSupported: List<String> = emptyList(),
    @SerialName("grant_types_supported") val grantTypesSupported: List<String> = emptyList(),
    @SerialName("dpop_signing_alg_values_supported") val dpopSigningAlgValuesSupported: List<String> = emptyList(),
    // AT Protocol-specific: client_id metadata document
    @SerialName("client_id_metadata_document_supported") val clientIdMetadataDocumentSupported: Boolean = false
)

/**
 * Resource server metadata, fetched from a PDS.
 * Points to the authorization server(s) that govern access to it.
 */
@Serializable
data class ResourceServerMetadata(
    val issuer: String,
    @SerialName("authorization_servers") val authorizationServers: List<String>? = null
)

/**
 * Response from a Pushed Authorization Request (PAR).
 * Contains a request_uri that the client presents at the auth endpoint.
 * Spec: RFC 9126
 */
@Serializable
data class ParResponse(
    @SerialName("request_uri") val requestUri: String,
    @SerialName("expires_in") val expiresIn: Int
)

/**
 * OAuth 2.0 token response.
 * In AT Protocol this always uses DPoP binding rather than Bearer.
 */
@Serializable
data class TokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("id_token") val idToken: String? = null,
    @SerialName("token_type") val tokenType: String,
    @SerialName("expires_in") val expiresIn: Int,
    val scope: String? = null,
    val sub: String? = null
)
