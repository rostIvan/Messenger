package trickyquestion.messenger.screen.chat.presenter;


import android.view.View;
import android.view.ViewGroup;

import trickyquestion.messenger.screen.chat.view.adapter.ChatViewHolder;

public interface IChatPresenter {
    void onCreate();
    void onDestroy();
    View.OnClickListener onNavigationButtonPressed();
    View.OnClickListener onSendButtonClick();
    void onClearMessagesItemCLick();

    int getCount();
    ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    void onBindViewHolder(ChatViewHolder holder, int position);
}
