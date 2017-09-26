package trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.RecyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.Holders.MessageViewHolder;
import trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Messages.IMessagePresenter;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.animation.ItemAlphaAnimator;

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
        ItemAlphaAnimator.setFadeAnimation(holder.itemView, Constants.DURATION_ITEM_ANIMATION);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }
}
