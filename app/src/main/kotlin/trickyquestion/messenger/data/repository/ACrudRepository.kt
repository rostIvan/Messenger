package trickyquestion.messenger.data.repository

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import java.lang.reflect.ParameterizedType

abstract class ACrudRepository<T: RealmModel> : IRepository<T> {
    val realm: Realm = Realm.getDefaultInstance()
    abstract fun equalsQuery(item: T) : RealmQuery<T>

    override fun save(item: T) {
        realm.executeTransaction { realm -> realm.copyToRealm(item) }
    }

    override fun delete(item: T) {
        val result = equalsQuery(item).findAll()
        realm.executeTransaction { result.deleteFirstFromRealm() }
    }

    fun realmQuery(): RealmQuery<T> = realm.where(clazz)
    private val className = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0].javaClass.name!!
    private val clazz: Class<T> = Class.forName(className) as Class<T>
}