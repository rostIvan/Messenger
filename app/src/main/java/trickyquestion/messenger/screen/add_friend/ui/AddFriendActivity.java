package trickyquestion.messenger.screen.add_friend.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import butterknife.BindView;
import trickyquestion.messenger.R;
import trickyquestion.messenger.di.PresentationFactory;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.add_friend.data.UserUtil;
import trickyquestion.messenger.ui.activity.AWithToolbarActivity;
import trickyquestion.messenger.ui.adapter.BaseRecyclerAdapter;
import trickyquestion.messenger.ui.interfaces.Layout;
import trickyquestion.messenger.ui.interfaces.BasePresenter;

import static trickyquestion.messenger.ui.util.ContextExtensionsKt.toast;

@Layout(res = R.layout.activity_add_friend)
public class AddFriendActivity extends AWithToolbarActivity implements IAddFriendView {

    @BindView(R.id.add_friend_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_add_friend)
    RecyclerView recyclerView;

    private IAddFriendPresenter presenter;

    @NonNull
    @Override
    public BasePresenter getPresenter() {
        presenter = PresentationFactory.INSTANCE.create(this);
        return presenter;
    }

    @Override
    public void showUsers(List<IUser> users) {
        recyclerView.setAdapter(new BaseRecyclerAdapter.Builder<IUser, AddFriendViewHolder>()
                .holder(AddFriendViewHolder.class)
                .itemView(R.layout.item_add_friend)
                .items(users)
                .bind(this::bindRecycler)
                .build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindRecycler(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        setupListValues(model, holder);
        setupListListeners(model, holder, items);
    }

    private void setupListValues(IUser model, AddFriendViewHolder holder) {
        holder.name.setText(UserUtil.getName(model));
        holder.id.setText(UserUtil.getId(model));
    }

    private void setupListListeners(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        holder.itemView.setOnClickListener(view -> presenter.onUserItemClick(model, holder, items) );
        holder.buttonAddFriend.setOnClickListener(view -> presenter.onUserItemClick(model, holder, items));
    }

    @Override
    public void updateUsers() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void removeItem(int position, int size) {
        recyclerView.removeViewAt(position);
        recyclerView.getAdapter().notifyItemRemoved(position);
        recyclerView.getAdapter().notifyItemRangeChanged(position, size);
    }

    @Override
    public void refreshTheme() { refreshThemeColor(); }

    @NonNull
    @Override
    protected Toolbar getToolbar() { return toolbar; }

    @NonNull
    @Override
    public CharSequence getToolbarTitle() { return "Add friend"; }
}
