package trickyquestion.messenger.data.repository

import de.greenrobot.event.EventBus
import io.realm.RealmQuery
import trickyquestion.messenger.screen.tabs.friends.data.Friend
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDbEvent
import java.util.*

open class FriendRepository : CrudRepository<Friend>() {
    companion object { @JvmField val INSTANCE = FriendRepository() }

    fun findById(uuid: UUID) = realmQuery().equalTo("id", uuid.toString()).findFirst()
    fun findAllByName(name: String): List<Friend> = realmQuery().equalTo("name", name).findAll() ?: emptyList()
    fun findByEncryptionKey(byteArray: ByteArray) = realmQuery().equalTo("encryptionKey", byteArray).findFirst()
    fun findAllOnline(): List<Friend> = realmQuery().equalTo("online", true).findAll() ?: emptyList()
    fun findAllOffline(): List<Friend> = realmQuery().equalTo("online", false).findAll()
    fun updateFriendStatus(friend: Friend, online: Boolean) {
        closeAfterTransaction{ friend.isOnline = online }
    }

    override fun equalsTo(item: Friend): RealmQuery<Friend> = realmQuery()
            .equalTo("id", item.id.toString())
            .equalTo("name", item.name)
            .equalTo("online", item.isOnline)
            .equalTo("encryptionKey", item.encryptionKey)

    override fun notifyDataChanged() {
        EventBus.getDefault().post(ChangeFriendDbEvent("friends db was changed"))
    }
}