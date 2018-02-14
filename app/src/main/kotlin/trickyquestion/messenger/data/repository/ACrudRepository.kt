package trickyquestion.messenger.data.repository

import io.realm.Realm
import io.realm.RealmModel
import java.lang.reflect.ParameterizedType

abstract class ACrudRepository<T: RealmModel> : IRepository<T> {
    val realm: Realm = Realm.getDefaultInstance()
    private fun realmQuery() = realm.where(clazz)


    private val className = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0].javaClass.name!!
    private val clazz: Class<T> = Class.forName(className) as Class<T>
}