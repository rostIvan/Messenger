package trickyquestion.messenger.data.repository

import io.realm.RealmQuery
import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend

abstract class TempRepository : ACrudRepository<Friend>() {

    override fun equalsQuery(item: Friend): RealmQuery<Friend> = realmQuery()
            .equalTo("id", item.id)
            .equalTo("name", item.name)
            .equalTo("online", item.isOnline)

    override fun notifyChanges() {
        TODO("not implemented")
    }
}