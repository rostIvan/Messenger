package trickyquestion.messenger.screen.add_friend.ui;

import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.ui.interfaces.BaseView;

public interface IAddFriendView extends BaseView {
    void showUsers(List<IUser> users);
    void refreshTheme();
    void updateUsers();
    void removeItem(int position, int size);
}
