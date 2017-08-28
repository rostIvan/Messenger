package trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends.IFriendsView;
import trickyquestion.messenger.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.name)
    TextView name;

    public FriendViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
