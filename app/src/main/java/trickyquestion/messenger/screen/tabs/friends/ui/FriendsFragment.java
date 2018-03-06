package trickyquestion.messenger.screen.tabs.friends.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;
import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.popup_windows.FriendPhotoDialog;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.abstraction.activity.ApplicationRouter;
import trickyquestion.messenger.ui.abstraction.adapter.BaseRecyclerAdapter;
import trickyquestion.messenger.ui.abstraction.interfaces.Layout;
import trickyquestion.messenger.ui.abstraction.mvp.fragment.MvpView;

import static trickyquestion.messenger.util.ContextExtensionsKt.toast;
import static trickyquestion.messenger.util.ViewUtilKt.greenColor;
import static trickyquestion.messenger.util.ViewUtilKt.redColor;

@Layout(res = R.layout.fragment_friends)
public class FriendsFragment extends MvpView implements IFriendsView {

    @BindView(R.id.rv_friends)
    RecyclerView recyclerView;

    private IFriendsPresenter presenter = getPresenter();

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public FriendsPresenter getPresenter() {
        return new FriendsPresenter(this, ApplicationRouter.from(getContext()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void showFriends(List<Friend> friends) {
        recyclerView.setAdapter(new BaseRecyclerAdapter.Builder<Friend, FriendViewHolder>()
                .holder(FriendViewHolder.class)
                .itemView(R.layout.item_friend)
                .items(friends)
                .bind(this::bindRecycler)
                .build());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onQueryTextChanged(newText);
                return false;
            }
        });
    }

    private void bindRecycler(Friend model, FriendViewHolder holder, List<Friend> items) {
        setupListValues(model, holder);
        setupListListeners(model, holder, items);
    }

    private void setupListValues(Friend model, FriendViewHolder holder) {
        holder.name.setText(model.getName());
        holder.onlineStatus.setText(model.isOnline() ? "online" : "offline");
        holder.onlineStatus.setTextColor(model.isOnline() ? greenColor(getContext()) : redColor(getContext()));
    }

    private void setupListListeners(Friend model, FriendViewHolder holder, List<Friend> items) {
        holder.itemView.setOnClickListener( v -> presenter.onFriendItemClick(model, holder, items) );
        holder.itemView.setOnCreateContextMenuListener((contextMenu, view, contextMenuInfo) -> createContextMenu(contextMenu, model));
        holder.image.setOnClickListener( v -> presenter.onFriendImageClick(model, holder, items) );
    }

    @Override
    public void updateFriends() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void removeItem(int position, int size) {
        recyclerView.removeViewAt(position);
        recyclerView.getAdapter().notifyItemRemoved(position);
        recyclerView.getAdapter().notifyItemRangeChanged(position, size);
    }

    @Override
    public void showFriendPhoto(Friend friend) {
        final FriendPhotoDialog dialog = FriendPhotoDialog.newInstance();
        final Bundle bundle = new Bundle();
        bundle.putString("name", friend.getName());
        bundle.putBoolean("online", friend.isOnline());
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "profile fragment");
    }

    @Override
    public void showToast(@NotNull CharSequence text) { toast(this, text); }

    @Override
    public void onUiThread(@NotNull Runnable runnable) { getActivity().runOnUiThread(runnable); }

    private void createContextMenu(ContextMenu contextMenu, Friend model) {
        contextMenu.setHeaderTitle(model.getName());
        contextMenu.add(0, 0, 0, "remove");
        contextMenu.getItem(0).setOnMenuItemClickListener(item -> {
            presenter.onFriendRemoveClick(model);
            return false;
        });
    }
}
