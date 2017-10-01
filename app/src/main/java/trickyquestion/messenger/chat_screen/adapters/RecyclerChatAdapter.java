package trickyquestion.messenger.chat_screen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import trickyquestion.messenger.chat_screen.presenter.IChatPresenter;

public class RecyclerChatAdapter extends RecyclerView.Adapter <ChatViewHolder> {

    private final IChatPresenter presenter;

    public RecyclerChatAdapter(final IChatPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return presenter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        presenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }
}
