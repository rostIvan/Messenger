package trickyquestion.messenger.chat_screen.presenter;


import android.view.View;
import android.view.ViewGroup;

import trickyquestion.messenger.chat_screen.adapters.ChatViewHolder;

public interface IChatPresenter {
    void onCreate();
    View.OnClickListener onNavigationButtonPressed();


    int getCount();
    ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    void onBindViewHolder(ChatViewHolder holder, int position);
}
