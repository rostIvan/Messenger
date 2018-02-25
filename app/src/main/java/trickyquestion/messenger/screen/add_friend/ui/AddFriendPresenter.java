package trickyquestion.messenger.screen.add_friend.ui;

import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.add_friend.buisness.AddFriendInteractor;
import trickyquestion.messenger.screen.add_friend.buisness.EventManager;
import trickyquestion.messenger.screen.add_friend.buisness.IAddFriendInteractor;
import trickyquestion.messenger.screen.add_friend.data.UserUtil;
import trickyquestion.messenger.ui.abstraction.interfaces.BaseRouter;
import trickyquestion.messenger.ui.abstraction.mvp.MvpPresenter;

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
        update();
    }

    @Override
    public void onUserItemClick(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        interactor.addToFriends(model);
        deleteUserItem(model, holder, items);
    }

    @Override
    public void updateUsers() {
        view.onUiThread(this::update);
    }

    @Override
    public void hideUsers() {
        view.showUsers(Collections.emptyList());
        view.updateUsers();
    }

    @Override
    public void changeTheme() { view.refreshTheme(); }

    private void update() {
        view.showUsers(interactor.getUsers());
        view.updateUsers();
    }

    private void deleteUserItem(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        view.showToast(String.format("User added: %s\nid: %s", UserUtil.getName(model), UserUtil.getId(model)));
        items.remove(holder.getAdapterPosition());
        view.updateUsers();
    }
}
