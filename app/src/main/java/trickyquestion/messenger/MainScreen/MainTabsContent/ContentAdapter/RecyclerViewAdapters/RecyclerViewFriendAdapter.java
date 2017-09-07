package trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.RecyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.FriendViewHolder;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends.IFriendPresenter;

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
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

}
