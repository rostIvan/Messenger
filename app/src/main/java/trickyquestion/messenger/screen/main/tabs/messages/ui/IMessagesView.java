package trickyquestion.messenger.screen.main.tabs.messages.ui;

import android.os.Bundle;

import java.util.List;

import trickyquestion.messenger.screen.main.tabs.messages.data.Message;
import trickyquestion.messenger.ui.interfaces.BaseView;

public interface IMessagesView extends BaseView {
    void showMessages(List<Message> messages);
    void showProgress(boolean show);
    void showFriendPhotoDialog(Bundle bundle);
    void setTitle(String title);
}