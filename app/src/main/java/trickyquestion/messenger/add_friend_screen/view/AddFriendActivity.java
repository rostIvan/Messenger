package trickyquestion.messenger.add_friend_screen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.add_friend_screen.adapter.RecyclerViewAddFriendAdapter;
import trickyquestion.messenger.add_friend_screen.presenter.AddFriendPresenter;
import trickyquestion.messenger.add_friend_screen.presenter.IAddFriendPresenter;
import trickyquestion.messenger.R;

public class AddFriendActivity extends AppCompatActivity  implements IAddFriendView {
    @BindView(R.id.rv_add_friend)
    RecyclerView recyclerView;

    private IAddFriendPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new AddFriendPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void showFriendsItems() {
        final RecyclerViewAddFriendAdapter adapter = new RecyclerViewAddFriendAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void notifyRecyclerDataChange() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyRecyclerItemRemove(final int item) {
        recyclerView.getAdapter().notifyItemChanged(item);
    }


}
