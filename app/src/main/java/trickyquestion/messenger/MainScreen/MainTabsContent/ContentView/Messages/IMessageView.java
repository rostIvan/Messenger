package trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Messages;

import android.content.Context;

public interface IMessageView {
    Context getFragmentContext();
    void showMessageContent();
    void setupSwipeRefreshLayout();
    void setRefreshing(final boolean isRefresh);

    void showFriendProfile();

    boolean isFriendProfileOpen();
}
