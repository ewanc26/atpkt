package uk.ewancroft.atpkt.generated.app.bsky.graph

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileView
import uk.ewancroft.atpkt.generated.app.bsky.actor.ProfileViewBasic
import uk.ewancroft.atpkt.generated.app.bsky.feed.GeneratorView
import uk.ewancroft.atpkt.generated.app.bsky.richtext.Facet
import uk.ewancroft.atpkt.generated.com.atproto.label.Label

@Serializable
public data class ListViewBasic(
  public val uri: String? = null,
  public val cid: String? = null,
  public val name: String? = null,
  public val purpose: ListPurpose? = null,
  public val avatar: String? = null,
  public val listItemCount: Long? = null,
  public val labels: List<Label?>? = null,
  public val viewer: ListViewerState? = null,
  public val indexedAt: String? = null,
)

@Serializable
public data class ListView(
  public val uri: String? = null,
  public val cid: String? = null,
  public val creator: ProfileView? = null,
  public val name: String? = null,
  public val purpose: ListPurpose? = null,
  public val description: String? = null,
  public val descriptionFacets: List<Facet?>? = null,
  public val avatar: String? = null,
  public val listItemCount: Long? = null,
  public val labels: List<Label?>? = null,
  public val viewer: ListViewerState? = null,
  public val indexedAt: String? = null,
)

@Serializable
public data class ListItemView(
  public val uri: String? = null,
  public val subject: ProfileView? = null,
)

@Serializable
public data class StarterPackView(
  public val uri: String? = null,
  public val cid: String? = null,
  public val record: JsonElement? = null,
  public val creator: ProfileViewBasic? = null,
  public val list: ListViewBasic? = null,
  public val listItemsSample: List<ListItemView?>? = null,
  public val feeds: List<GeneratorView?>? = null,
  public val joinedWeekCount: Long? = null,
  public val joinedAllTimeCount: Long? = null,
  public val labels: List<Label?>? = null,
  public val indexedAt: String? = null,
)

@Serializable
public data class StarterPackViewBasic(
  public val uri: String? = null,
  public val cid: String? = null,
  public val record: JsonElement? = null,
  public val creator: ProfileViewBasic? = null,
  public val listItemCount: Long? = null,
  public val joinedWeekCount: Long? = null,
  public val joinedAllTimeCount: Long? = null,
  public val labels: List<Label?>? = null,
  public val indexedAt: String? = null,
)

@Serializable
public data class ListViewerState(
  public val muted: Boolean? = null,
  public val blocked: String? = null,
)

@Serializable
public data class NotFoundActor(
  public val actor: String? = null,
  public val notFound: Boolean? = null,
)

@Serializable
public data class Relationship(
  public val did: String? = null,
  public val following: String? = null,
  public val followedBy: String? = null,
  public val blocking: String? = null,
  public val blockedBy: String? = null,
  public val blockingByList: String? = null,
  public val blockedByList: String? = null,
)
