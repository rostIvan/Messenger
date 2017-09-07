package trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.RecyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.MessageViewHolder;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Messages.IMessagePresenter;

public class RecyclerViewMessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private final IMessagePresenter presenter;

    public RecyclerViewMessageAdapter(final IMessagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return presenter.onCreateView(parent, viewType);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        presenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }
}
