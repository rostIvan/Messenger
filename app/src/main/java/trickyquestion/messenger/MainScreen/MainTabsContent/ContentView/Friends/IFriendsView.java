package trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends;

import android.content.Context;

public interface IFriendsView {
    Context getFragmentContext();
    void showFriendsItem();
    void notifyRecyclerDataChange();
}
