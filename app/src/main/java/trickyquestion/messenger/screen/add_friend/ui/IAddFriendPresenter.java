package trickyquestion.messenger.screen.add_friend.ui;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public interface IAddFriendPresenter {
    void onUserItemClick(IUser model, AddFriendViewHolder holder, List<IUser> items);
    void updateUsers();
    void hideUsers();
    void changeTheme();
}
