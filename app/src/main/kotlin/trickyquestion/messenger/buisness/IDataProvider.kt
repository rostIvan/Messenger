package trickyquestion.messenger.buisness

import trickyquestion.messenger.p2p_protocol.interfaces.IUser
import trickyquestion.messenger.screen.tabs.friends.data.Friend

interface IDataProvider {
    fun getFriendsFromDb() : List<Friend>
    fun getUsersFromNetwork() : List<IUser>
}
