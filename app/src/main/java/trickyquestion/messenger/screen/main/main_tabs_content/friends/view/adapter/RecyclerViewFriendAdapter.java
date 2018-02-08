package trickyquestion.messenger.screen.main.main_tabs_content.friends.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import trickyquestion.messenger.screen.main.main_tabs_content.friends.presenter.IFriendPresenter;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.android.view.ItemAlphaAnimator;

public class RecyclerViewFriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {

    private final IFriendPresenter presenter;

    public RecyclerViewFriendAdapter(final IFriendPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return presenter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        presenter.onBindViewHolder(holder, position);
        ItemAlphaAnimator.setFadeAnimation(holder.itemView, Constants.DURATION_ITEM_ANIMATION);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

}
