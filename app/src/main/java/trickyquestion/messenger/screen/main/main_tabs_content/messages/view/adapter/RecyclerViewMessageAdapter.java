package trickyquestion.messenger.screen.main.main_tabs_content.messages.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import trickyquestion.messenger.screen.main.main_tabs_content.messages.presenter.IMessagePresenter;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.android.view.ItemAlphaAnimator;

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
