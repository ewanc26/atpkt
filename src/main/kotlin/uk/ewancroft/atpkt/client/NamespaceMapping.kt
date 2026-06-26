package uk.ewancroft.atpkt.client

object NamespaceMapping {
    val mappings: Map<String, String> = mapOf(
        // chat.bsky.actor
        "chat.bsky.actor.declaration" to "chat.bsky.actor.declaration",
        "chat.bsky.actor.deleteAccount" to "chat.bsky.actor.deleteAccount",
        "chat.bsky.actor.exportAccountData" to "chat.bsky.actor.exportAccountData",
        "chat.bsky.actor.getStatus" to "chat.bsky.actor.getStatus",

        // chat.bsky.convo
        "chat.bsky.convo.acceptConvo" to "chat.bsky.convo.acceptConvo",
        "chat.bsky.convo.addReaction" to "chat.bsky.convo.addReaction",
        "chat.bsky.convo.deleteMessageForSelf" to "chat.bsky.convo.deleteMessageForSelf",
        "chat.bsky.convo.getConvo" to "chat.bsky.convo.getConvo",
        "chat.bsky.convo.getConvoAvailability" to "chat.bsky.convo.getConvoAvailability",
        "chat.bsky.convo.getConvoForMembers" to "chat.bsky.convo.getConvoForMembers",
        "chat.bsky.convo.getConvoMembers" to "chat.bsky.convo.getConvoMembers",
        "chat.bsky.convo.getLog" to "chat.bsky.convo.getLog",
        "chat.bsky.convo.getMessages" to "chat.bsky.convo.getMessages",
        "chat.bsky.convo.leaveConvo" to "chat.bsky.convo.leaveConvo",
        "chat.bsky.convo.listConvoRequests" to "chat.bsky.convo.listConvoRequests",
        "chat.bsky.convo.listConvos" to "chat.bsky.convo.listConvos",
        "chat.bsky.convo.lockConvo" to "chat.bsky.convo.lockConvo",
        "chat.bsky.convo.muteConvo" to "chat.bsky.convo.muteConvo",
        "chat.bsky.convo.removeReaction" to "chat.bsky.convo.removeReaction",
        "chat.bsky.convo.sendMessage" to "chat.bsky.convo.sendMessage",
        "chat.bsky.convo.sendMessageBatch" to "chat.bsky.convo.sendMessageBatch",
        "chat.bsky.convo.unlockConvo" to "chat.bsky.convo.unlockConvo",
        "chat.bsky.convo.unmuteConvo" to "chat.bsky.convo.unmuteConvo",
        "chat.bsky.convo.updateAllRead" to "chat.bsky.convo.updateAllRead",
        "chat.bsky.convo.updateRead" to "chat.bsky.convo.updateRead",

        // chat.bsky.group
        "chat.bsky.group.addMembers" to "chat.bsky.group.addMembers",
        "chat.bsky.group.approveJoinRequest" to "chat.bsky.group.approveJoinRequest",
        "chat.bsky.group.createGroup" to "chat.bsky.group.createGroup",
        "chat.bsky.group.createJoinLink" to "chat.bsky.group.createJoinLink",
        "chat.bsky.group.disableJoinLink" to "chat.bsky.group.disableJoinLink",
        "chat.bsky.group.editGroup" to "chat.bsky.group.editGroup",
        "chat.bsky.group.editJoinLink" to "chat.bsky.group.editJoinLink",
        "chat.bsky.group.enableJoinLink" to "chat.bsky.group.enableJoinLink",
        "chat.bsky.group.getJoinLinkPreviews" to "chat.bsky.group.getJoinLinkPreviews",
        "chat.bsky.group.listJoinRequests" to "chat.bsky.group.listJoinRequests",
        "chat.bsky.group.listMutualGroups" to "chat.bsky.group.listMutualGroups",
        "chat.bsky.group.rejectJoinRequest" to "chat.bsky.group.rejectJoinRequest",
        "chat.bsky.group.removeMembers" to "chat.bsky.group.removeMembers",
        "chat.bsky.group.requestJoin" to "chat.bsky.group.requestJoin",

        // chat.bsky.moderation
        "chat.bsky.moderation.getActorMetadata" to "chat.bsky.moderation.getActorMetadata",
        "chat.bsky.moderation.getMessageContext" to "chat.bsky.moderation.getMessageContext",
        "chat.bsky.moderation.subscribeModEvents" to "chat.bsky.moderation.subscribeModEvents",
        "chat.bsky.moderation.updateActorAccess" to "chat.bsky.moderation.updateActorAccess",

        // tools.ozone.communication
        "tools.ozone.communication.createTemplate" to "tools.ozone.communication.createTemplate",
        "tools.ozone.communication.deleteTemplate" to "tools.ozone.communication.deleteTemplate",
        "tools.ozone.communication.listTemplates" to "tools.ozone.communication.listTemplates",
        "tools.ozone.communication.updateTemplate" to "tools.ozone.communication.updateTemplate",

        // tools.ozone.hosting
        "tools.ozone.hosting.getAccountHistory" to "tools.ozone.hosting.getAccountHistory",

        // tools.ozone.moderation
        "tools.ozone.moderation.cancelScheduledActions" to "tools.ozone.moderation.cancelScheduledActions",
        "tools.ozone.moderation.emitEvent" to "tools.ozone.moderation.emitEvent",
        "tools.ozone.moderation.getAccountTimeline" to "tools.ozone.moderation.getAccountTimeline",
        "tools.ozone.moderation.getEvent" to "tools.ozone.moderation.getEvent",
        "tools.ozone.moderation.getRecord" to "tools.ozone.moderation.getRecord",
        "tools.ozone.moderation.getRecords" to "tools.ozone.moderation.getRecords",
        "tools.ozone.moderation.getRepo" to "tools.ozone.moderation.getRepo",
        "tools.ozone.moderation.getReporterStats" to "tools.ozone.moderation.getReporterStats",
        "tools.ozone.moderation.getRepos" to "tools.ozone.moderation.getRepos",
        "tools.ozone.moderation.getSubjects" to "tools.ozone.moderation.getSubjects",
        "tools.ozone.moderation.listScheduledActions" to "tools.ozone.moderation.listScheduledActions",
        "tools.ozone.moderation.queryEvents" to "tools.ozone.moderation.queryEvents",
        "tools.ozone.moderation.queryStatuses" to "tools.ozone.moderation.queryStatuses",
        "tools.ozone.moderation.scheduleAction" to "tools.ozone.moderation.scheduleAction",
        "tools.ozone.moderation.searchRepos" to "tools.ozone.moderation.searchRepos",

        // tools.ozone.queue
        "tools.ozone.queue.assignModerator" to "tools.ozone.queue.assignModerator",
        "tools.ozone.queue.createQueue" to "tools.ozone.queue.createQueue",
        "tools.ozone.queue.deleteQueue" to "tools.ozone.queue.deleteQueue",
        "tools.ozone.queue.getAssignments" to "tools.ozone.queue.getAssignments",
        "tools.ozone.queue.listQueues" to "tools.ozone.queue.listQueues",
        "tools.ozone.queue.routeReports" to "tools.ozone.queue.routeReports",
        "tools.ozone.queue.unassignModerator" to "tools.ozone.queue.unassignModerator",
        "tools.ozone.queue.updateQueue" to "tools.ozone.queue.updateQueue",

        // tools.ozone.report
        "tools.ozone.report.assignModerator" to "tools.ozone.report.assignModerator",
        "tools.ozone.report.createActivity" to "tools.ozone.report.createActivity",
        "tools.ozone.report.getAssignments" to "tools.ozone.report.getAssignments",
        "tools.ozone.report.getHistoricalStats" to "tools.ozone.report.getHistoricalStats",
        "tools.ozone.report.getLatestReport" to "tools.ozone.report.getLatestReport",
        "tools.ozone.report.getLiveStats" to "tools.ozone.report.getLiveStats",
        "tools.ozone.report.getReport" to "tools.ozone.report.getReport",
        "tools.ozone.report.listActivities" to "tools.ozone.report.listActivities",
        "tools.ozone.report.queryReports" to "tools.ozone.report.queryReports",
        "tools.ozone.report.reassignQueue" to "tools.ozone.report.reassignQueue",
        "tools.ozone.report.refreshStats" to "tools.ozone.report.refreshStats",
        "tools.ozone.report.unassignModerator" to "tools.ozone.report.unassignModerator",

        // tools.ozone.safelink
        "tools.ozone.safelink.addRule" to "tools.ozone.safelink.addRule",
        "tools.ozone.safelink.queryEvents" to "tools.ozone.safelink.queryEvents",
        "tools.ozone.safelink.queryRules" to "tools.ozone.safelink.queryRules",
        "tools.ozone.safelink.removeRule" to "tools.ozone.safelink.removeRule",
        "tools.ozone.safelink.updateRule" to "tools.ozone.safelink.updateRule",

        // tools.ozone.server
        "tools.ozone.server.getConfig" to "tools.ozone.server.getConfig",

        // tools.ozone.set
        "tools.ozone.set.addValues" to "tools.ozone.set.addValues",
        "tools.ozone.set.deleteSet" to "tools.ozone.set.deleteSet",
        "tools.ozone.set.deleteValues" to "tools.ozone.set.deleteValues",
        "tools.ozone.set.getValues" to "tools.ozone.set.getValues",
        "tools.ozone.set.querySets" to "tools.ozone.set.querySets",
        "tools.ozone.set.upsertSet" to "tools.ozone.set.upsertSet",

        // tools.ozone.setting
        "tools.ozone.setting.listOptions" to "tools.ozone.setting.listOptions",
        "tools.ozone.setting.removeOptions" to "tools.ozone.setting.removeOptions",
        "tools.ozone.setting.upsertOption" to "tools.ozone.setting.upsertOption",

        // tools.ozone.signature
        "tools.ozone.signature.findCorrelation" to "tools.ozone.signature.findCorrelation",
        "tools.ozone.signature.findRelatedAccounts" to "tools.ozone.signature.findRelatedAccounts",
        "tools.ozone.signature.searchAccounts" to "tools.ozone.signature.searchAccounts",

        // tools.ozone.team
        "tools.ozone.team.addMember" to "tools.ozone.team.addMember",
        "tools.ozone.team.deleteMember" to "tools.ozone.team.deleteMember",
        "tools.ozone.team.listMembers" to "tools.ozone.team.listMembers",
        "tools.ozone.team.updateMember" to "tools.ozone.team.updateMember",

        // tools.ozone.verification
        "tools.ozone.verification.grantVerifications" to "tools.ozone.verification.grantVerifications",
        "tools.ozone.verification.listVerifications" to "tools.ozone.verification.listVerifications",
        "tools.ozone.verification.revokeVerifications" to "tools.ozone.verification.revokeVerifications",
    )
}
