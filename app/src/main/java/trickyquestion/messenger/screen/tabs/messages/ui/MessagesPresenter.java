package trickyquestion.messenger.screen.tabs.messages.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.chat.view.ChatActivity;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.tabs.messages.buisness.IMessagesInteractor;
import trickyquestion.messenger.screen.tabs.messages.buisness.MessagesInteractor;
import trickyquestion.messenger.screen.tabs.messages.data.Message;
import trickyquestion.messenger.ui.abstraction.interfaces.BaseRouter;
import trickyquestion.messenger.ui.abstraction.mvp.fragment.MvpPresenter;
import trickyquestion.messenger.util.AnimatorResource;

public class MessagesPresenter extends MvpPresenter<MessagesFragment, BaseRouter> implements IMessagesPresenter {
    private final IMessagesView view = getView();
    private final BaseRouter router = getRouter();
    private final IMessagesInteractor interactor = new MessagesInteractor();

    MessagesPresenter(@NotNull MessagesFragment view, @NotNull BaseRouter router) {
        super(view, router);
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
        bundle.putString("name", model.getFriend().getName());
        bundle.putBoolean("online", model.getFriend().isOnline());
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

    private void showMessages() {
        view.showMessages(interactor.getMessages());
    }

    private void emulateUpdating() {
        view.showProgress(true);
        new Handler().postDelayed(() -> view.showProgress(false), 1_000);
    }

    private void openChat(Bundle bundle) {
        router.use(getView()).openScreen(BaseRouter.Screen.CHAT, bundle,
                AnimatorResource.with(R.anim.translate_left_slide, R.anim.translate_right_slide));
    }
}
