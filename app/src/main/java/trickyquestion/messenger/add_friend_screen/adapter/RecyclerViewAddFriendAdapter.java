package trickyquestion.messenger.add_friend_screen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import trickyquestion.messenger.add_friend_screen.presenter.IAddFriendPresenter;

public class RecyclerViewAddFriendAdapter extends RecyclerView.Adapter<AddFriendViewHolder> {
    private final IAddFriendPresenter presenter;

    public RecyclerViewAddFriendAdapter(final IAddFriendPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public AddFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return presenter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(AddFriendViewHolder holder, int position) {
        presenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

}

