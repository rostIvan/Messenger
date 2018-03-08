package trickyquestion.messenger.screen.tabs.messages.ui;

import trickyquestion.messenger.screen.tabs.messages.data.Message;

public interface IMessagesPresenter {
    void onMessageItemClick(Message model);
    void onFriendPhotoClick(Message model);
    void onQueryTextChanged(String newText);
    void onRefresh();
}
