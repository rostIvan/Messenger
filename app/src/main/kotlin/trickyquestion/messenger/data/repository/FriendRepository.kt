package trickyquestion.messenger.data.repository

import io.realm.RealmQuery
import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend
import java.util.*

class FriendRepository : CrudRepository<Friend>() {

    fun findById(uuid: UUID) = realmQuery().equalTo("id", uuid.toString()).findFirst()
    fun findAllByName(name: String) = realmQuery().equalTo("name", name).findAll()!!
    fun findAllOnline() = realmQuery().equalTo("online", true).findAll()
    fun findAllOffline() = realmQuery().equalTo("online", false).findAll()

    override fun equalsTo(item: Friend): RealmQuery<Friend> = realmQuery()
            .equalTo("id", item.id.toString())
            .equalTo("name", item.name)
            .equalTo("online", item.isOnline)

    override fun notifyDataChanged() {
        TODO("notify")
    }
}