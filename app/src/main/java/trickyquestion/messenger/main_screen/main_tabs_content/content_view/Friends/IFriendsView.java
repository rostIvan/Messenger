package trickyquestion.messenger.main_screen.main_tabs_content.content_view.Friends;

import android.content.Context;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;

public interface IFriendsView {
    Context getFragmentContext();

    void showFriendsItems();

    void notifyRecyclerDataChange();

    void showFriendProfile(String name);

    String getSearchQuery();

    void showChatActivity(Friend message);
}
