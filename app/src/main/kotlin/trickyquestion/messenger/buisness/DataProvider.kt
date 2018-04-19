package trickyquestion.messenger.buisness

import trickyquestion.messenger.data.repository.FriendRepository
import trickyquestion.messenger.network.Network
import trickyquestion.messenger.network.NetworkState
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector
import trickyquestion.messenger.p2p_protocol.interfaces.IUser
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend

open class DataProvider :  IDataProvider {
    private val friendRepository = FriendRepository.INSTANCE

    override fun getFriendsFromDb(): List<Friend> = friendRepository.findAll()

    override fun getUsersFromNetwork(): List<IUser> {
        return when(Network.GetCurrentNetworkState()) {
            NetworkState.ACTIVE -> P2PProtocolConnector.ProtocolInterface().users
            else -> { emptyList() }
        }
    }
}