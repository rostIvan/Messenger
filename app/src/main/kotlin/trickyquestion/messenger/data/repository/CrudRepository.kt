package trickyquestion.messenger.data.repository

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import io.realm.kotlin.where
import java.lang.reflect.ParameterizedType

abstract class CrudRepository<T : RealmModel> : IRepository<T> {
    private val clazz: Class<T> = getClazzFromGeneric()
    private fun realm() = Realm.getDefaultInstance()
    protected abstract fun equalsTo(item: T): RealmQuery<T>

    fun realmQuery(): RealmQuery<T> = realm().where(clazz)

    override fun save(item: T) {
        closeAfterTransaction { it.copyToRealm(item) }
    }

    override fun delete(item: T) {
        closeAfterTransaction { equalsTo(item).findAll().deleteFirstFromRealm() }
    }

    override fun saveAll(items: Iterable<T>) {
        closeAfterTransaction { it.copyToRealm(items) }
    }

    override fun deleteAll(items: Iterable<T>) {
        items.forEach { item -> delete(item) }
    }

    override fun deleteAll() {
        closeAfterTransaction { it.delete(clazz) }
    }

    override fun findAll(): List<T> = realmQuery().findAll()
    override fun first(): T = findAll().first()
    override fun last(): T = findAll().last()
    override fun count(): Int = findAll().size
    override fun isEmpty(): Boolean = (count() == 0)

    private fun inTransaction(operation: () -> Unit) {
        realm().executeTransaction { operation.invoke() }
        notifyDataChanged()
    }

    fun closeAfterTransaction(operation: (Realm) -> Unit) {
        realm().use { inTransaction { operation.invoke(it) } }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getClazzFromGeneric() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
}