package uk.ewancroft.atpkt.generated.app.bsky.actor

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.app.bsky.graph.ListViewBasic
import uk.ewancroft.atpkt.generated.app.bsky.graph.StarterPackViewBasic
import uk.ewancroft.atpkt.generated.app.bsky.notification.ActivitySubscription
import uk.ewancroft.atpkt.generated.com.atproto.label.Label
import uk.ewancroft.atpkt.generated.com.atproto.repo.StrongRef

@Serializable
public data class ProfileViewBasic(
  public val did: String? = null,
  public val handle: String? = null,
  public val displayName: String? = null,
  public val pronouns: String? = null,
  public val avatar: String? = null,
  public val associated: ProfileAssociated? = null,
  public val viewer: ViewerState? = null,
  public val labels: List<Label?>? = null,
  public val createdAt: String? = null,
  public val verification: VerificationState? = null,
  public val status: StatusView? = null,
  public val debug: JsonElement? = null,
)

@Serializable
public data class ProfileView(
  public val did: String? = null,
  public val handle: String? = null,
  public val displayName: String? = null,
  public val pronouns: String? = null,
  public val description: String? = null,
  public val avatar: String? = null,
  public val associated: ProfileAssociated? = null,
  public val indexedAt: String? = null,
  public val createdAt: String? = null,
  public val viewer: ViewerState? = null,
  public val labels: List<Label?>? = null,
  public val verification: VerificationState? = null,
  public val status: StatusView? = null,
  public val debug: JsonElement? = null,
)

@Serializable
public data class ProfileViewDetailed(
  public val did: String? = null,
  public val handle: String? = null,
  public val displayName: String? = null,
  public val description: String? = null,
  public val pronouns: String? = null,
  public val website: String? = null,
  public val avatar: String? = null,
  public val banner: String? = null,
  public val followersCount: Long? = null,
  public val followsCount: Long? = null,
  public val postsCount: Long? = null,
  public val associated: ProfileAssociated? = null,
  public val joinedViaStarterPack: StarterPackViewBasic? = null,
  public val indexedAt: String? = null,
  public val createdAt: String? = null,
  public val viewer: ViewerState? = null,
  public val labels: List<Label?>? = null,
  public val pinnedPost: StrongRef? = null,
  public val verification: VerificationState? = null,
  public val status: StatusView? = null,
  public val debug: JsonElement? = null,
)

@Serializable
public data class ProfileAssociated(
  public val lists: Long? = null,
  public val feedgens: Long? = null,
  public val starterPacks: Long? = null,
  public val labeler: Boolean? = null,
  public val chat: ProfileAssociatedChat? = null,
  public val activitySubscription: ProfileAssociatedActivitySubscription? = null,
  public val germ: ProfileAssociatedGerm? = null,
)

@Serializable
public data class ProfileAssociatedChat(
  public val allowIncoming: String? = null,
  public val allowGroupInvites: String? = null,
)

@Serializable
public data class ProfileAssociatedGerm(
  public val messageMeUrl: String? = null,
  public val showButtonTo: String? = null,
)

@Serializable
public data class ProfileAssociatedActivitySubscription(
  public val allowSubscriptions: String? = null,
)

@Serializable
public data class ViewerState(
  public val muted: Boolean? = null,
  public val mutedByList: ListViewBasic? = null,
  public val blockedBy: Boolean? = null,
  public val blocking: String? = null,
  public val blockingByList: ListViewBasic? = null,
  public val following: String? = null,
  public val followedBy: String? = null,
  public val knownFollowers: KnownFollowers? = null,
  public val activitySubscription: ActivitySubscription? = null,
)

@Serializable
public data class KnownFollowers(
  public val count: Long? = null,
  public val followers: List<ProfileViewBasic?>? = null,
)

@Serializable
public data class VerificationState(
  public val verifications: List<VerificationView?>? = null,
  public val verifiedStatus: String? = null,
  public val trustedVerifierStatus: String? = null,
)

@Serializable
public data class VerificationView(
  public val issuer: String? = null,
  public val uri: String? = null,
  public val isValid: Boolean? = null,
  public val createdAt: String? = null,
)

@Serializable
public data class AdultContentPref(
  public val enabled: Boolean? = null,
)

@Serializable
public data class ContentLabelPref(
  public val labelerDid: String? = null,
  public val label: String? = null,
  public val visibility: String? = null,
)

@Serializable
public data class SavedFeed(
  public val id: String? = null,
  public val type: String? = null,
  public val `value`: String? = null,
  public val pinned: Boolean? = null,
)

@Serializable
public data class SavedFeedsPrefV2(
  public val items: List<SavedFeed?>? = null,
)

@Serializable
public data class SavedFeedsPref(
  public val pinned: List<String?>? = null,
  public val saved: List<String?>? = null,
  public val timelineIndex: Long? = null,
)

@Serializable
public data class PersonalDetailsPref(
  public val birthDate: String? = null,
)

@Serializable
public data class DeclaredAgePref(
  public val isOverAge13: Boolean? = null,
  public val isOverAge16: Boolean? = null,
  public val isOverAge18: Boolean? = null,
)

@Serializable
public data class FeedViewPref(
  public val feed: String? = null,
  public val hideReplies: Boolean? = null,
  public val hideRepliesByUnfollowed: Boolean? = null,
  public val hideRepliesByLikeCount: Long? = null,
  public val hideReposts: Boolean? = null,
  public val hideQuotePosts: Boolean? = null,
)

@Serializable
public data class ThreadViewPref(
  public val sort: String? = null,
)

@Serializable
public data class InterestsPref(
  public val tags: List<String?>? = null,
)

@Serializable
public data class MutedWord(
  public val id: String? = null,
  public val `value`: String? = null,
  public val targets: List<MutedWordTarget?>? = null,
  public val actorTarget: String? = null,
  public val expiresAt: String? = null,
)

@Serializable
public data class MutedWordsPref(
  public val items: List<MutedWord?>? = null,
)

@Serializable
public data class HiddenPostsPref(
  public val items: List<String?>? = null,
)

@Serializable
public data class LabelersPref(
  public val labelers: List<LabelerPrefItem?>? = null,
)

@Serializable
public data class LabelerPrefItem(
  public val did: String? = null,
)

@Serializable
public data class BskyAppStatePref(
  public val activeProgressGuide: BskyAppProgressGuide? = null,
  public val queuedNudges: List<String?>? = null,
  public val nuxs: List<Nux?>? = null,
)

@Serializable
public data class BskyAppProgressGuide(
  public val guide: String? = null,
)

@Serializable
public data class Nux(
  public val id: String? = null,
  public val completed: Boolean? = null,
  public val `data`: String? = null,
  public val expiresAt: String? = null,
)

@Serializable
public data class VerificationPrefs(
  public val hideBadges: Boolean? = null,
)

@Serializable
public data class LiveEventPreferences(
  public val hiddenFeedIds: List<String?>? = null,
  public val hideAllFeeds: Boolean? = null,
)

@Serializable
public sealed interface ThreadgateAllowRulesUnion

@Serializable
public sealed interface PostgateEmbeddingRulesUnion

@Serializable
public data class PostInteractionSettingsPref(
  public val threadgateAllowRules: List<ThreadgateAllowRulesUnion?>? = null,
  public val postgateEmbeddingRules: List<PostgateEmbeddingRulesUnion?>? = null,
)

@Serializable
public sealed interface EmbedUnion

@Serializable
public data class StatusView(
  public val uri: String? = null,
  public val cid: String? = null,
  public val status: String? = null,
  public val record: JsonElement? = null,
  public val embed: EmbedUnion? = null,
  public val labels: List<Label?>? = null,
  public val expiresAt: String? = null,
  public val isActive: Boolean? = null,
  public val isDisabled: Boolean? = null,
)
