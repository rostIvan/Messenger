package trickyquestion.messenger.buisness

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import trickyquestion.messenger.data.repository.FriendRepository
import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend

open class DataProviderInteractor : IDataProvider {

    private val friendRepository = FriendRepository
    private val compositeDisposable = CompositeDisposable()
    override fun getFriendsFromDb(): List<Friend> = friendRepository.findAll()

    @JvmOverloads
    fun subscribeOnUpdates(onUpdate: Consumer<List<Friend>>, t: Consumer<Throwable> = Consumer {}) {
        compositeDisposable.add(friendRepository.
                publishSubject
//                .subscribeOn(Schedulers.io())
                .subscribe(onUpdate, t))
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

    override fun getUsersFromNetwork(): List<Any> {
        TODO("not implemented")
    }

}

interface IDataProvider {
    fun getFriendsFromDb() : List<Friend>
    fun getUsersFromNetwork() : List<Any>
}
