package trickyquestion.messenger.screen.main.main_tabs_content.messages.view;

import android.content.Context;

import trickyquestion.messenger.screen.main.main_tabs_content.messages.model.Message;

public interface IMessageView {
    Context getFragmentContext();
    void showMessageContent();

    void notifyDataSetChanged();

    void setupSwipeRefreshLayout();
    void setRefreshing(final boolean isRefresh);

    void showChatActivity(Message message);
    void showFriendProfile(String nameSender, boolean b);

    void setToolbarTitle(CharSequence title);

    CharSequence getAppName();
}
