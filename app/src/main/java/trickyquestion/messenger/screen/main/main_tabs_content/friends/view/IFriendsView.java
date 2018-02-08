package trickyquestion.messenger.screen.main.main_tabs_content.friends.view;

import android.content.Context;

import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend;

public interface IFriendsView {
    Context getFragmentContext();

    void showFriendsItems();

    void notifyRecyclerDataChange();

    void showFriendProfile(String name, boolean online);

    String getSearchQuery();

    void showChatActivity(Friend message);

    void runOnUiThread(Runnable r);
}
