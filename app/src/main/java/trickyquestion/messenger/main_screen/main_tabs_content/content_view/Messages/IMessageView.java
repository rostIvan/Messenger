package trickyquestion.messenger.main_screen.main_tabs_content.content_view.Messages;

import android.content.Context;

public interface IMessageView {
    Context getFragmentContext();
    void showMessageContent();
    void setupSwipeRefreshLayout();
    void setRefreshing(final boolean isRefresh);

    void showFriendProfile();
    boolean isFriendProfileOpen();

    void dismissPhotoDialog();
}
