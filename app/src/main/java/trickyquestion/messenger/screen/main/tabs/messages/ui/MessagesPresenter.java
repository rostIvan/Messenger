package trickyquestion.messenger.screen.main.tabs.messages.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import trickyquestion.messenger.R;
import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.screen.chat.ui.ChatActivity;
import trickyquestion.messenger.screen.popup_windows.FriendPhotoDialog;
import trickyquestion.messenger.screen.main.tabs.messages.buisness.MessagesEventManager;
import trickyquestion.messenger.screen.main.tabs.messages.buisness.IMessagesInteractor;
import trickyquestion.messenger.screen.main.tabs.messages.data.Message;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.fragment.MvpPresenter;
import trickyquestion.messenger.ui.util.AnimatorResource;

public class MessagesPresenter extends MvpPresenter<IMessagesView, BaseRouter> implements IMessagesPresenter {
    private final IMessagesInteractor interactor;
    private BaseEventManager messagesEventManager;

    public MessagesPresenter(@NotNull IMessagesView view,
                             @NotNull BaseRouter router,
                             @NotNull IMessagesInteractor messagesInteractor) {
        super(view, router);
        this.interactor = messagesInteractor;
    }

    @Override
    public void attach(@NonNull BaseEventManager messagesEventManager) {
        this.messagesEventManager = messagesEventManager;
    }

    @Override
    public void onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        messagesEventManager.subscribe();
    }

    @Override
    public void onDestroyView() {
        messagesEventManager.unsubscribe();
    }

    @Override
    public void onViewCreated(@Nullable View v, @Nullable Bundle savedInstanceState) {
        showMessages();
    }

    @Override
    public void onMessageItemClick(Message model) {
        final Bundle bundle = new Bundle();
        bundle.putString(ChatActivity.FRIEND_ID_EXTRA, model.getFriend().getId().toString());
        bundle.putString(ChatActivity.FRIEND_NAME_EXTRA, model.getFriend().getName());
        openChat(bundle);
    }

    @Override
    public void onFriendPhotoClick(Message model) {
        final Bundle bundle = new Bundle();
        bundle.putString(FriendPhotoDialog.FRIEND_NAME_EXTRA, model.getFriend().getName());
        bundle.putBoolean(FriendPhotoDialog.FRIEND_ONLINE_EXTRA, model.getFriend().isOnline());
        getView().showFriendPhotoDialog(bundle);
    }

    @Override
    public void onQueryTextChanged(String query) {
        getView().showMessages(interactor.getMessages(query));
    }

    @Override
    public void onRefresh() {
        getView().onUiThread(this::emulateUpdating);
    }

    @Override
    public void updateMessages() {
        getView().onUiThread(this::showMessages);
    }

    @Override
    public void clearMessagesDeletedFriend() {
        interactor.deleteEmptyTables();
    }

    private void showMessages() {
        getView().showMessages(interactor.getMessages());
    }

    private void emulateUpdating() {
        getView().showProgress(true);
        new Handler().postDelayed(() -> {
            updateMessages();
            getView().showProgress(false);
        }, 1_000);
    }

    private void openChat(Bundle bundle) {
        getRouter().openScreen(BaseRouter.Screen.CHAT, bundle,
                        AnimatorResource.with(R.anim.translate_left_slide, R.anim.translate_right_slide));
    }
}
