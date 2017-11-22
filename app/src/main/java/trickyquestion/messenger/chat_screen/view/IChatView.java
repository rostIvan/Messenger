package trickyquestion.messenger.chat_screen.view;


import android.view.View;
import android.widget.TextView;

public interface IChatView {
    void customizeTheme();

    void customizeToolbar();

    void setupListeners();

    void showMessages();
    void goBack();

    void refreshRecycler();
    void scrollRecyclerToPosition(final int position);

    String getMessageText();
    void clearMessageText();
    void showToast(String text);

    String getFriendName();

    void setStyleForMyMessage(View container, TextView textMessage, TextView timeMessage);

    void setStyleForFriendMessage(View container, TextView textMessage, TextView timeMessage);
}
