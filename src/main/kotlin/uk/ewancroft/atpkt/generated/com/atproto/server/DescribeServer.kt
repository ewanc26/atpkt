package uk.ewancroft.atpkt.generated.com.atproto.server

import kotlin.String
import kotlinx.serialization.Serializable

@Serializable
public data class DescribeServerOutput(
  public val unused: String? = null,
)

@Serializable
public data class Links(
  public val privacyPolicy: String? = null,
  public val termsOfService: String? = null,
)

@Serializable
public data class Contact(
  public val email: String? = null,
)
