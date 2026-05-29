package uk.ewancroft.atpkt.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationServerMetadata(
    val issuer: String,
    @SerialName("authorization_endpoint") val authorizationEndpoint: String,
    @SerialName("token_endpoint") val tokenEndpoint: String,
    @SerialName("jwks_uri") val jwksUri: String? = null,
    @SerialName("pushed_authorization_request_endpoint") val parEndpoint: String? = null,
    @SerialName("require_pushed_authorization_requests") val requirePar: Boolean = false,
    @SerialName("response_types_supported") val responseTypesSupported: List<String> = emptyList(),
    @SerialName("grant_types_supported") val grantTypesSupported: List<String> = emptyList(),
    @SerialName("dpop_signing_alg_values_supported") val dpopSigningAlgValuesSupported: List<String> = emptyList(),
    @SerialName("client_id_metadata_document_supported") val clientIdMetadataDocumentSupported: Boolean = false
)

@Serializable
data class ResourceServerMetadata(
    val issuer: String,
    @SerialName("authorization_servers") val authorizationServers: List<String>? = null
)

@Serializable
data class ParResponse(
    @SerialName("request_uri") val requestUri: String,
    @SerialName("expires_in") val expiresIn: Int
)

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
