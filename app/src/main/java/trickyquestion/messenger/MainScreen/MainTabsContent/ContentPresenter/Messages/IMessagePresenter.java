package trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Messages;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ViewGroup;

import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.MessageViewHolder;

public interface IMessagePresenter {
    //for fragment
    void onCreateView();
    int[] getSchemeColors();
    int getProgressBackgroundColor();
    SwipeRefreshLayout.OnRefreshListener onRefreshListener();

    // for Adapter
    MessageViewHolder onCreateView(final ViewGroup parent, final int viewType);
    void onBindViewHolder(final MessageViewHolder holder, final int position);
    int getCount();
}
