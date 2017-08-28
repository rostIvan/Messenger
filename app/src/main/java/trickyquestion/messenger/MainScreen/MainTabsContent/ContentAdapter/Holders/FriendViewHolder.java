package trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends.IFriendsView;
import trickyquestion.messenger.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    public FriendViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
    }
}
