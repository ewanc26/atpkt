package uk.ewancroft.atpkt.agent

import uk.ewancroft.atpkt.core.AtProtoClient
import uk.ewancroft.atpkt.core.AtProtoSessionManager
import uk.ewancroft.atpkt.client.ComAtProtoNS
import uk.ewancroft.atpkt.client.AppBskyNS

/**
 * The central agent class for AT Protocol interactions.
 * Inspired by the official TypeScript SDK's Agent architecture.
 */
class Agent(
    val sessionManager: AtProtoSessionManager
) {
    val com = ComAtProtoNS(this)
    val app = AppBskyNS(this)
}
