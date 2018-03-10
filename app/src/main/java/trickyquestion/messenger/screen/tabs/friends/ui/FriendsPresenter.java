package trickyquestion.messenger.screen.tabs.friends.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.chat.view.ChatActivity;
import trickyquestion.messenger.screen.popup_windows.FriendPhotoDialog;
import trickyquestion.messenger.screen.tabs.friends.buisness.EventManager;
import trickyquestion.messenger.screen.tabs.friends.buisness.FriendsInteractor;
import trickyquestion.messenger.screen.tabs.friends.buisness.IFriendsInteractor;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.fragment.MvpPresenter;
import trickyquestion.messenger.ui.util.AnimatorResource;

public class FriendsPresenter extends MvpPresenter<FriendsFragment, BaseRouter> implements IFriendsPresenter {
    private final IFriendsView view = getView();
    private final IFriendsInteractor interactor = new FriendsInteractor();
    private final EventManager eventManager = new EventManager(this);
    private final BaseRouter router = getRouter();

    FriendsPresenter(@NotNull FriendsFragment view, @NotNull BaseRouter router) {
        super(view, router);
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
    public void onDestroyView() { eventManager.unsubscribe(); }

    @Override
    public void updateFriends() {
        getView().onUiThread(this::showFriends);
    }

    private void showFriends() {
        view.showFriends(interactor.getFriends());
    }

    @Override
    public void onFriendItemClick(Friend model, FriendViewHolder holder, List<Friend> items) {
        final Bundle bundle = new Bundle();
        bundle.putString(ChatActivity.FRIEND_ID_EXTRA, model.getId().toString());
        bundle.putString(ChatActivity.FRIEND_NAME_EXTRA, model.getName());
        openChat(bundle);
    }

    private void openChat(Bundle bundle) {
        router.use(getView()).openScreen(BaseRouter.Screen.CHAT, bundle,
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
        view.showFriendPhoto(bundle);
    }

    @Override
    public void onQueryTextChanged(String query) {
        view.showFriends(interactor.getFriends(query));
    }

    @Override
    public void onNetworkStateChanged(NetworkState state) {
        switch (state) {
            case ACTIVE:  updateFriends(); break;
            case INACTIVE: view.showToast("You haven't connection"); break;
        }
    }

    @Override
    public void onChangeFriendState(IUser user, boolean online) {
        interactor.updateFriendStatus(user, online);
    }
}
