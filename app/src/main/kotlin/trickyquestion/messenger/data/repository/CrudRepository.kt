package trickyquestion.messenger.data.repository

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import java.lang.reflect.ParameterizedType

abstract class CrudRepository<T : RealmModel> : IRepository<T> {
    private val clazz: Class<T> = getClazzFromGeneric()
    private fun realm() = Realm.getDefaultInstance()
    protected abstract fun equalsTo(item: T): RealmQuery<T>

    fun realmQuery(): RealmQuery<T> = realm().where(clazz)


    override fun save(item: T) {
        closeAfter { it.copyToRealm(item) }
    }

    override fun delete(item: T) {
        closeAfter {
            val result = equalsTo(item).findAll()
            result.deleteFirstFromRealm()
        }
    }

    override fun saveAll(items: Iterable<T>) {
        closeAfter { it.copyToRealm(items) }
    }

    override fun deleteAll(items: Iterable<T>) {
        items.forEach { item -> delete(item) }
    }

    override fun deleteAll() {
        closeAfter { it.delete(clazz) }
    }

    override fun findAll(): List<T> = realmQuery().findAll()
    override fun first(): T = findAll().first()
    override fun last(): T = findAll().last()
    override fun count(): Int = findAll().size
    override fun isEmpty(): Boolean = (count() == 0)

    fun inTransaction(operation: () -> Unit) {
        realm().executeTransaction { operation.invoke() }
        notifyDataChanged()
    }

    fun closeAfter(operation: (Realm) -> Unit) {
        realm().use { inTransaction { operation.invoke(it) } }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getClazzFromGeneric() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
}