package trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.FriendViewHolder;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends.IFriendsView;
import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Friend;
import trickyquestion.messenger.R;

public class FriendPresenter implements IFriendPresenter {

    private final IFriendsView view;
    private final List<Friend> friendList;

    public FriendPresenter(final IFriendsView view) {
        this.view = view;
        this.friendList = Friend.getFriends(40);
    }

    @Override
    public void onCreateView() {
        view.showFriendsItem();
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        final Friend friend = friendList.get(position);
        setViewValue(holder, friend);
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_friend, parent, false
        );
        return new FriendViewHolder(itemView);
    }

    private void setViewValue(final FriendViewHolder holder, Friend friend) {
        holder.name.setText(friend.getName());
        holder.id.setText(friend.getId().toString().substring(0, 25).replace("-", ""));
        holder.onlineStatus.setText(friend.isOnline() ? "online" : "offline");
        if (friend.isOnline()) holder.onlineStatus.setTextColor(Color.GREEN);
        else holder.onlineStatus.setTextColor(Color.argb(1000, 1000, 1000, 1000));
    }
}
