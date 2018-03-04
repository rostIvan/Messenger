package trickyquestion.messenger.screen.tabs.friends.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.screen.chat.view.ChatActivity;
import trickyquestion.messenger.screen.tabs.friends.buisness.EventManager;
import trickyquestion.messenger.screen.tabs.friends.buisness.FriendInteractor;
import trickyquestion.messenger.screen.tabs.friends.buisness.IFriendInteractor;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.abstraction.activity.ApplicationRouter;
import trickyquestion.messenger.ui.abstraction.interfaces.BaseRouter;
import trickyquestion.messenger.ui.abstraction.mvp.fragment.MvpPresenter;
import trickyquestion.messenger.ui.abstraction.mvp.fragment.MvpView;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeUserList;

public class FriendPresenter extends MvpPresenter<FriendsFragment, BaseRouter> implements IFriendPresenter {
    private IFriendsView view = getView();
    private IFriendInteractor interactor = new FriendInteractor();
    private EventManager eventManager = new EventManager(this);
    private BaseRouter router = getRouter();

    FriendPresenter(@NotNull FriendsFragment view, @NotNull BaseRouter router) {
        super(view, router);
    }

    @Override
    public void onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eventManager.subscribe();
        showFriends();
    }

    @Override
    public void onDestroyView() { eventManager.unsubscribe(); }

    @Override
    public void updateFriends() {
        view.onUiThread(this::showFriends);
    }

    private void showFriends() {
        view.showFriends(interactor.getFriends());
    }

    @Override
    public void onFriendItemClick(Friend model, FriendViewHolder holder, List<Friend> items) {
        final Bundle bundle = new Bundle();
        bundle.putString(ChatActivity.FRIEND_ID_EXTRA, model.getId().toString());
        bundle.putString(ChatActivity.FRIEND_NAME_EXTRA, model.getName());
        router.from(getView()).openScreen(BaseRouter.Screen.CHAT, bundle,
                new ApplicationRouter.AnimatorResource(R.anim.translate_left_slide, R.anim.translate_right_slide));
    }

    @Override
    public void onFriendRemoveClick(Friend model) {
        interactor.deleteFriend(model);
    }

    @Override
    public void onFriendImageClick(Friend model, FriendViewHolder holder, List<Friend> items) {
        view.showFriendPhoto(model);
    }

    @Override
    public void onQueryTextChanged(String query) {
        view.showFriends(interactor.getFriends(query));
    }

    @Override
    public void onNetworkStateChanged(ENetworkStateChanged event) {
        switch (event.getNewNetworkState()) {
            case ACTIVE:  updateFriends(); break;
            case INACTIVE:
                view.showFriends(Collections.emptyList());
                view.showToast("You haven't connection"); break;
        }
    }

    @Override
    public void onChangeFriendState(ChangeUserList event) {
        interactor.updateFriendStatus(event.getUser(), event.exist());
    }
}
