package trickyquestion.messenger.screen.chat.ui;

import java.util.List;

import trickyquestion.messenger.screen.chat.data.ChatMessage;
import trickyquestion.messenger.ui.interfaces.BaseView;

public interface IChatView extends BaseView {
    void showChatMessages(List<ChatMessage> messages);
    void updateMessages();

    void clearInputText();

    void hideSendButton();
    void showSendButton();
}
