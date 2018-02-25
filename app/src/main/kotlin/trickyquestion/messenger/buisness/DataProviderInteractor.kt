package trickyquestion.messenger.buisness

import io.reactivex.functions.Consumer
import trickyquestion.messenger.data.repository.FriendRepository
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector
import trickyquestion.messenger.p2p_protocol.interfaces.IUser
import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend

open class DataProviderInteractor : BaseInteractor(), IDataProvider {

    private val friendRepository = FriendRepository
    override fun getFriendsFromDb(): List<Friend> = friendRepository.findAll()

    @JvmOverloads
    fun subscribeOnUpdates(onUpdate: Consumer<List<Friend>>, t: Consumer<Throwable> = Consumer {}) {
        addDisposable(friendRepository.publishSubject.subscribe(onUpdate, t))
    }

    override fun getUsersFromNetwork(): List<IUser> = P2PProtocolConnector.ProtocolInterface().users

}

interface IDataProvider {
    fun getFriendsFromDb() : List<Friend>
    fun getUsersFromNetwork() : List<IUser>
}
