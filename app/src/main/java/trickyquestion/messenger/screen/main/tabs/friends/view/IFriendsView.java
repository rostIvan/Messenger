package trickyquestion.messenger.screen.main.tabs.friends.view;

import android.content.Context;

import trickyquestion.messenger.screen.tabs.friends.data.Friend;

public interface IFriendsView {
    Context getFragmentContext();

    void showFriendsItems();

    void notifyRecyclerDataChange();

    void showFriendProfile(String name, boolean online);

    String getSearchQuery();

    void showChatActivity(Friend message);

    void runOnUiThread(Runnable r);
}
