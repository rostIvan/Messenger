package trickyquestion.messenger.data.repository

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import java.lang.reflect.ParameterizedType

abstract class CrudRepository<T : RealmModel> : IRepository<T> {
    private val realm: Realm = Realm.getDefaultInstance()
    private val clazz: Class<T> = getClazzFromGeneric()
    protected abstract fun equalsTo(item: T): RealmQuery<T>

    fun realmQuery(): RealmQuery<T> = realm.where(clazz)

    override fun save(item: T) {
        realm.executeTransaction { it.copyToRealm(item) }
    }

    override fun delete(item: T) {
        val result = equalsTo(item).findAll()
        realm.executeTransaction { result.deleteFirstFromRealm() }
    }

    override fun saveAll(items: Iterable<T>) {
        realm.executeTransaction { it.copyToRealm(items) }
    }

    override fun deleteAll(items: Iterable<T>) {
        items.forEach { item -> delete(item) }
    }

    override fun deleteAll() {
        realm.executeTransaction { it.delete(clazz) }
    }

    override fun findAll(): List<T> = realmQuery().findAll()
    override fun first(): T = findAll().first()
    override fun last(): T = findAll().last()
    override fun count(): Int = findAll().size

    @Suppress("UNCHECKED_CAST")
    private fun getClazzFromGeneric() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
}