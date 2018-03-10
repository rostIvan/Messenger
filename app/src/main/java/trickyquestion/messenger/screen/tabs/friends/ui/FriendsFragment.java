package trickyquestion.messenger.screen.tabs.friends.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;
import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.popup_windows.FriendPhotoDialog;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.adapter.BaseRecyclerAdapter;
import trickyquestion.messenger.ui.fragment.AWithSearchFragment;
import trickyquestion.messenger.ui.interfaces.Layout;

import static trickyquestion.messenger.ui.util.ContextExtensionsKt.toast;
import static trickyquestion.messenger.ui.util.ViewUtilKt.greenColor;
import static trickyquestion.messenger.ui.util.ViewUtilKt.redColor;

@Layout(res = R.layout.fragment_friends)
public class FriendsFragment extends AWithSearchFragment implements IFriendsView {

    @BindView(R.id.rv_friends)
    RecyclerView recyclerView;

    private FriendsPresenter presenter = new FriendsPresenter(this, ApplicationRouter.from(getContext()));

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public FriendsPresenter getPresenter() { return presenter; }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        addSearchListener(newText -> presenter.onQueryTextChanged(newText.toString()));
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
        holder.image.setOnClickListener( v -> presenter.onFriendImageClick(model) );
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
    public void showFriendPhoto(Bundle bundle) {
        final FriendPhotoDialog dialog = FriendPhotoDialog.newInstance(bundle);
        dialog.show(getFragmentManager(), "profile fragment");
    }

    private void createContextMenu(ContextMenu contextMenu, Friend model) {
        contextMenu.setHeaderTitle(model.getName());
        contextMenu.add(0, 0, 0, "remove");
        contextMenu.getItem(0).setOnMenuItemClickListener(item -> {
            presenter.onFriendRemoveClick(model);
            return false;
        });
    }
}
