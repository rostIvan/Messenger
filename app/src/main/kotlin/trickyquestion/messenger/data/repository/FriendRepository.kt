package trickyquestion.messenger.data.repository

import io.reactivex.subjects.PublishSubject
import io.realm.RealmQuery
import trickyquestion.messenger.screen.main.tabs.friends.model.Friend
import java.util.*

object FriendRepository : CrudRepository<Friend>() {
    val publishSubject: PublishSubject<List<Friend>> = PublishSubject.create<List<Friend>>()

    fun findById(uuid: UUID) = realmQuery().equalTo("id", uuid.toString()).findFirst()
    fun findAllByName(name: String): List<Friend> = realmQuery().equalTo("name", name).findAll()
    fun findByEncryptionKey(byteArray: ByteArray) = realmQuery().equalTo("encryptionKey", byteArray).findFirst()
    fun findAllOnline(): List<Friend> = realmQuery().equalTo("online", true).findAll()
    fun findAllOffline(): List<Friend> = realmQuery().equalTo("online", false).findAll()

    override fun equalsTo(item: Friend): RealmQuery<Friend> = realmQuery()
            .equalTo("id", item.id.toString())
            .equalTo("name", item.name)
            .equalTo("online", item.isOnline)
            .equalTo("encryptionKey", item.encryptionKey)

    override fun notifyDataChanged() {
        publishSubject.onNext(findAll())
    }
}