package trickyquestion.messenger.data.repository

import io.realm.RealmModel

interface IRepository<T : RealmModel> {
    fun save(item: T)
    fun delete(item: T)

    fun saveAll(items: Iterable<T>)
    fun deleteAll(items: Iterable<T>)
    fun deleteAll()
    fun findAll() : List<T>
    fun first() : T
    fun last() : T
    fun count() : Int

    fun notifyDataChanged()
}