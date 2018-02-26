package trickyquestion.messenger.screen.add_friend.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import butterknife.BindView;
import trickyquestion.messenger.R;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.screen.add_friend.data.UserUtil;
import trickyquestion.messenger.ui.abstraction.activity.AWithToolbarActivity;
import trickyquestion.messenger.ui.abstraction.activity.ApplicationRouter;
import trickyquestion.messenger.ui.abstraction.adapter.BaseRecyclerAdapter;
import trickyquestion.messenger.ui.abstraction.interfaces.Layout;

import static trickyquestion.messenger.util.ContextExtensionsKt.toast;

@Layout(res = R.layout.activity_add_friend)
public class AddFriendActivity extends AWithToolbarActivity implements IAddFriendView {

    @BindView(R.id.add_friend_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_add_friend)
    RecyclerView recyclerView;

    private final IAddFriendPresenter presenter = getPresenter();

    @NonNull
    @Override
    public AddFriendPresenter getPresenter() { return new AddFriendPresenter(this, ApplicationRouter.from(this)); }

    @Override
    public void showUsers(List<IUser> userList) {
        recyclerView.setAdapter(new BaseRecyclerAdapter.Builder<IUser, AddFriendViewHolder>()
                .holder(AddFriendViewHolder.class)
                .itemView(R.layout.item_add_friend)
                .items(userList)
                .bind(this::bindRecycler)
                .build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindRecycler(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        setupListValues(model, holder, items);
        setupListListeners(model, holder, items);
    }

    private void setupListValues(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        holder.name.setText(UserUtil.getName(model));
        holder.id.setText(UserUtil.getId(model));
    }

    private void setupListListeners(IUser model, AddFriendViewHolder holder, List<IUser> items) {
        holder.itemView.setOnClickListener(view -> presenter.onUserItemClick(model, holder, items) );
        holder.buttonAddFriend.setOnClickListener(view -> presenter.onUserItemClick(model, holder, items));
    }

    @Override
    public void updateUsers() { recyclerView.getAdapter().notifyDataSetChanged(); }

    @Override
    public void refreshTheme() { refreshThemeColor(); }

    @Override
    public void showToast(@NonNull final CharSequence text) { toast(this, text); }

    @NonNull
    @Override
    protected Toolbar getToolbar() { return toolbar; }

    @NonNull
    @Override
    public CharSequence getToolbarTitle() { return "Add friend"; }

    @Override
    public void onUiThread(@NonNull Runnable runnable) { super.runOnUiThread(runnable); }
}
