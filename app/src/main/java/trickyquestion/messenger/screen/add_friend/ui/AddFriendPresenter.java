package trickyquestion.messenger.screen.add_friend.ui;

import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.add_friend.buisness.AddFriendInteractor;
import trickyquestion.messenger.screen.add_friend.buisness.EventManager;
import trickyquestion.messenger.screen.add_friend.buisness.IAddFriendInteractor;
import trickyquestion.messenger.screen.add_friend.data.UserUtil;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.ui.mvp.activity.MvpPresenter;

public class AddFriendPresenter extends MvpPresenter<AddFriendActivity, BaseRouter> implements IAddFriendPresenter {
    private final IAddFriendView view = getView();
    private final EventManager eventManager = new EventManager(this);
    private final IAddFriendInteractor interactor = new AddFriendInteractor();

    AddFriendPresenter(@NotNull AddFriendActivity view, @NotNull BaseRouter router) {
        super(view, router);
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) { eventManager.subscribe(); }

    @Override
    public void onDestroy() { eventManager.unsubscribe(); }

    @Override
    public void onStart() {
        showUsers();
    }

    @Override
    public void onUserItemClick(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        interactor.addToFriends(model);
        deleteUserItem(holder, items);
        view.showToast(String.format("User added: %s\nid: %s", UserUtil.getName(model), UserUtil.getId(model)));
    }

    @Override
    public void updateUsers() {
        view.onUiThread(this::showUsers);
    }

    @Override
    public void hideUsers() {
        view.showUsers(Collections.emptyList());
    }

    @Override
    public void changeTheme() { view.refreshTheme(); }

    private void showUsers() {
        switch (Network.GetCurrentNetworkState()) {
            case ACTIVE: view.showUsers(interactor.getUsers()); break;
            case INACTIVE: view.showToast("You haven't connection"); break;
        }
    }

    private void deleteUserItem(AddFriendViewHolder holder, List<IUser> items) {
        final int position = holder.getAdapterPosition();
        items.remove(position);
        view.removeItem(position, items.size());
    }
}
