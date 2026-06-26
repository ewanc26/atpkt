package uk.ewancroft.atpkt.client

import uk.ewancroft.atpkt.agent.Agent

class ToolsNS(private val agent: Agent) {
    val ozone = ToolsOzoneNS(agent)
}
