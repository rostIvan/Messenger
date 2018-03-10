package trickyquestion.messenger.screen.tabs.messages.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.chat.view.ChatActivity;
import trickyquestion.messenger.screen.popup_windows.FriendPhotoDialog;
import trickyquestion.messenger.screen.tabs.messages.buisness.EventManager;
import trickyquestion.messenger.screen.tabs.messages.buisness.IMessagesInteractor;
import trickyquestion.messenger.screen.tabs.messages.buisness.MessagesInteractor;
import trickyquestion.messenger.screen.tabs.messages.data.Message;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.fragment.MvpPresenter;
import trickyquestion.messenger.ui.util.AnimatorResource;

public class MessagesPresenter extends MvpPresenter<MessagesFragment, BaseRouter> implements IMessagesPresenter {
    private final IMessagesView view = getView();
    private final BaseRouter router = getRouter();
    private final IMessagesInteractor interactor = new MessagesInteractor();
    private final EventManager eventManager = new EventManager(this);

    MessagesPresenter(@NotNull MessagesFragment view, @NotNull BaseRouter router) {
        super(view, router);
    }

    @Override
    public void onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eventManager.subscribe();
    }

    @Override
    public void onDestroyView() {
        eventManager.unsubscribe();
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
        view.showFriendPhotoDialog(bundle);
    }

    @Override
    public void onQueryTextChanged(String query) {
        view.showMessages(interactor.getMessages(query));
    }

    @Override
    public void onRefresh() {
        view.onUiThread(this::emulateUpdating);
    }

    @Override
    public void updateMessages() {
        view.onUiThread(this::showMessages);
    }

    @Override
    public void clearMessagesDeletedFriend() {
        interactor.deleteEmptyTables();
    }

    private void showMessages() {
        view.showMessages(interactor.getMessages());
    }

    private void emulateUpdating() {
        view.showProgress(true);
        new Handler().postDelayed(() -> {
            updateMessages();
            view.showProgress(false);
        }, 1_000);
    }

    private void openChat(Bundle bundle) {
        router.use(getView()).openScreen(BaseRouter.Screen.CHAT, bundle,
                AnimatorResource.with(R.anim.translate_left_slide, R.anim.translate_right_slide));
    }
}
