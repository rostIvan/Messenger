package trickyquestion.messenger.screen.main.tabs.friends.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.chat.ui.ChatActivity;
import trickyquestion.messenger.screen.main.tabs.friends.buisness.FriendsEventManager;
import trickyquestion.messenger.screen.main.tabs.friends.buisness.IFriendsInteractor;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.popup_windows.FriendPhotoDialog;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.fragment.MvpPresenter;
import trickyquestion.messenger.ui.util.AnimatorResource;

import static trickyquestion.messenger.network.NetworkState.ACTIVE;
import static trickyquestion.messenger.network.NetworkState.INACTIVE;

public class FriendsPresenter extends MvpPresenter<IFriendsView, BaseRouter> implements IFriendsPresenter {
    private final IFriendsInteractor interactor;
    private BaseEventManager eventManager;

    public FriendsPresenter(@NotNull IFriendsView view,
                            @NotNull BaseRouter router,
                            @NotNull IFriendsInteractor friendsInteractor) {
        super(view, router);
        this.interactor = friendsInteractor;
    }

    @Override
    public void attach(@NonNull BaseEventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eventManager.subscribe();
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        showFriends();
    }

    @Override
    public void onDestroyView() {
        eventManager.unsubscribe();
    }

    @Override
    public void updateFriends() {
        getView().onUiThread(this::showFriends);
    }

    private void showFriends() {
        getView().showFriends(interactor.getFriends());
    }

    @Override
    public void onFriendItemClick(Friend model, List<Friend> items) {
        final Bundle bundle = new Bundle();
        bundle.putString(ChatActivity.FRIEND_ID_EXTRA, model.getId().toString());
        bundle.putString(ChatActivity.FRIEND_NAME_EXTRA, model.getName());
        openChat(bundle);
    }

    private void openChat(Bundle bundle) {
        getRouter().openScreen(BaseRouter.Screen.CHAT, bundle,
                AnimatorResource.with(R.anim.translate_left_slide, R.anim.alpha_to_zero));
    }

    @Override
    public void onFriendRemoveClick(Friend model) {
        interactor.deleteFriend(model);
    }

    @Override
    public void onFriendImageClick(Friend model) {
        final Bundle bundle = new Bundle();
        bundle.putString(FriendPhotoDialog.FRIEND_NAME_EXTRA, model.getName());
        bundle.putBoolean(FriendPhotoDialog.FRIEND_ONLINE_EXTRA, model.isOnline());
        getView().showFriendPhoto(bundle);
    }

    @Override
    public void onQueryTextChanged(String query) {
        getView().showFriends(interactor.getFriends(query));
    }

    @Override
    public void onNetworkStateChanged(NetworkState state) {
        if (state == ACTIVE) updateFriends();
        else if (state == INACTIVE) getView().showToast("You haven't connection");
    }

    @Override
    public void onChangeFriendState(IUser user, boolean online) {
        interactor.updateFriendStatus(user, online);
    }
}
