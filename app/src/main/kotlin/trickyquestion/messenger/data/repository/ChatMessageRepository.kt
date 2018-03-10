package trickyquestion.messenger.data.repository

import de.greenrobot.event.EventBus
import io.realm.RealmQuery
import trickyquestion.messenger.screen.tabs.chat.data.ChatMessage
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeMessageDbEvent
import java.util.*


open class ChatMessageRepository : CrudRepository<ChatMessage>() {
    companion object { @JvmField val INSTANCE = ChatMessageRepository() }

    fun findFriendMessages(id: UUID) = findAll().filter { message -> message.friendId == id }
    fun findLastFriendMessage(id: UUID) = findFriendMessages(id).lastOrNull()
    fun deleteFriendMessages(id: UUID) {
        deleteAll(findFriendMessages(id))
    }

    override fun equalsTo(item: ChatMessage): RealmQuery<ChatMessage> = realmQuery()
            .equalTo("text", item.text)
            .equalTo("mine", item.isMine)
            // equals friend
            .equalTo("friendId", item.friendId.toString())
            // equals date model
            .equalTo("date.second", item.date.second)
            .equalTo("date.minute", item.date.minute)
            .equalTo("date.hour", item.date.hour)
            .equalTo("date.day", item.date.day)
            .equalTo("date.month", item.date.month)
            .equalTo("date.year", item.date.year)

    override fun notifyDataChanged() {
        EventBus.getDefault().post(ChangeMessageDbEvent("Message db changed"))
    }
}