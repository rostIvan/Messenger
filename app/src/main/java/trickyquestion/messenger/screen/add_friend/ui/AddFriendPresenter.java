package trickyquestion.messenger.screen.add_friend.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import trickyquestion.messenger.buisness.BaseEventManager;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.add_friend.buisness.AddFriendEventManager;
import trickyquestion.messenger.screen.add_friend.buisness.IAddFriendInteractor;
import trickyquestion.messenger.screen.add_friend.data.UserUtil;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.activity.MvpPresenter;

public class AddFriendPresenter extends MvpPresenter<IAddFriendView, BaseRouter> implements IAddFriendPresenter {
    private final IAddFriendInteractor interactor;
    private BaseEventManager addFriendEventManager;

    public AddFriendPresenter(@NotNull IAddFriendView view,
                       @NotNull BaseRouter router,
                       @NotNull IAddFriendInteractor addFriendInteractor) {
        super(view, router);
        this.interactor = addFriendInteractor;
    }

    @Override
    public void attach(@NonNull BaseEventManager addFriendEventManager) {
        this.addFriendEventManager = addFriendEventManager;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) { addFriendEventManager.subscribe(); }

    @Override
    public void onDestroy() { addFriendEventManager.unsubscribe(); }

    @Override
    public void onStart() {
        showUsers();
    }

    @Override
    public void onUserItemClick(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        interactor.addToFriends(model);
        deleteUserItem(holder, items);
        getView().showToast(String.format("User added: %s\nid: %s", UserUtil.getName(model), UserUtil.getId(model)));
    }

    @Override
    public void updateUsers() {
        getView().onUiThread(this::showUsers);
    }

    @Override
    public void hideUsers() {
        getView().showUsers(Collections.emptyList());
    }

    @Override
    public void changeTheme() { getView().refreshTheme(); }

    private void showUsers() {
        if (Network.getCurrentNetworkState() == NetworkState.ACTIVE)
            getView().showUsers(interactor.getUsers());
        else if(Network.getCurrentNetworkState() == NetworkState.INACTIVE)
            getView().showToast("You haven't connection");
    }

    private void deleteUserItem(AddFriendViewHolder holder, List<IUser> items) {
        final int position = holder.getAdapterPosition();
        items.remove(position);
        getView().removeItem(position, items.size());
    }
}
