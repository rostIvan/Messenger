package trickyquestion.messenger.data.repository

interface IRepository<T> {
    fun save(item: T)
    fun delete(item: T)

    fun saveAll(items: Iterable<T>)
    fun deleteAll(items: Iterable<T>)
    fun deleteAll()

    fun findAll() : List<T>

    fun first() : T
    fun last() : T
    fun count() : Int
    fun isEmpty(): Boolean

    fun notifyDataChanged()
}