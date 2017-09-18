package trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.RecyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.Holders.FriendViewHolder;
import trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Friends.IFriendPresenter;

//TODO: clean imports

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
