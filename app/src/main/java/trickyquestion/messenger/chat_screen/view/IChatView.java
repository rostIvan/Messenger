package trickyquestion.messenger.chat_screen.view;


public interface IChatView {
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
}
