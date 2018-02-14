package trickyquestion.messenger.data.repository

import io.realm.RealmModel

interface IRepository<T : RealmModel> {
    fun save(item: T)
    fun delete(item: T)

    fun saveAll(items: Iterable<T>)
    fun deleteAll(items: Iterable<T>)
    fun deleteAll()
    fun findAll() : List<T>
    fun find() : T

    fun update(from: T, to: T)
    fun updateAll(from: T, to: T)

    fun notifyChanges()
}