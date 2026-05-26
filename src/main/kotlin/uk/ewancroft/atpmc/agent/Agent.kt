package uk.ewancroft.atpmc.agent

import uk.ewancroft.atpmc.core.AtProtoClient
import uk.ewancroft.atpmc.core.AtProtoSessionManager
import uk.ewancroft.atpmc.client.ComAtProtoNS
import uk.ewancroft.atpmc.client.AppBskyNS

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
