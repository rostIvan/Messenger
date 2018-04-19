package trickyquestion.messenger.screen.main.tabs.messages.ui;

import trickyquestion.messenger.screen.main.tabs.messages.data.Message;
import trickyquestion.messenger.ui.interfaces.BasePresenter;

public interface IMessagesPresenter extends BasePresenter {
    void onMessageItemClick(Message model);
    void onFriendPhotoClick(Message model);
    void onQueryTextChanged(String newText);
    void onRefresh();
    void updateMessages();
    void clearMessagesDeletedFriend();
}
