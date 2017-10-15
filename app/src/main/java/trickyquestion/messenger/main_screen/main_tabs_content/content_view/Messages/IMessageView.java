package trickyquestion.messenger.main_screen.main_tabs_content.content_view.Messages;

import android.content.Context;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;

public interface IMessageView {
    Context getFragmentContext();
    void showMessageContent();

    void notifyDataSetChanged();

    void setupSwipeRefreshLayout();
    void setRefreshing(final boolean isRefresh);

    void showFriendProfile();
    boolean isFriendProfileOpen();
    void dismissPhotoDialog();

    void showChatActivity(Message message);
}
