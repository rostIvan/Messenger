package trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import trickyquestion.messenger.AddFriendScreen.AddFriendActivity;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.FriendViewHolder;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends.FriendsFragment;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends.IFriendsView;
import trickyquestion.messenger.MainScreen.MainTabsContent.Interactors.FriendListInteractor;
import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Friend;
import trickyquestion.messenger.MainScreen.View.MainActivity;
import trickyquestion.messenger.R;
import trickyquestion.messenger.Util.Animation.ItemAlphaAnimator;
import trickyquestion.messenger.Util.Constants;

public class FriendPresenter implements IFriendPresenter {

    private IFriendsView view;
    private List<Friend> friendList;
    private static boolean profileWasOpened;

    public FriendPresenter(final IFriendsView view) {
        this.view = view;
        this.friendList = FriendListInteractor.getFriends();
    }

    /** For Fragment */
    @Override
    public void onCreateView() {
        view.showFriendsItems();
        view.setFabBehavior();
    }


    @Override
    public void onStart() {
        if (profileWasOpened) view.showFriendProfile();
    }

    @Override
    public RecyclerView.OnScrollListener fabOnScroll() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0)
//                    view.hideFab();
//                else if (dy < 0)
//                    view.showFab();
            }
        };
    }

    @Override
    public View.OnClickListener onFabClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startAddFriendActivity();
            }
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        profileWasOpened = view.isFriendProfileOpen();
        view.dismissPhotoDialog();
    }

    @Override
    public SearchView.OnQueryTextListener onQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(view.getFragmentContext(), "Submit: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                friendList = FriendListInteractor.getFriends(newText);
                view.notifyRecyclerDataChange();
                return false;
            }
        };
    }

    /** For Recycler */
    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        final Friend friend = friendList.get(position);
        setViewValue(holder, friend);
        ItemAlphaAnimator.setFadeAnimation(holder.itemView, Constants.DURATION_ITEM_ANIMATION);
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
        holder.id.setText(friend.getId().toString().substring(0, 25).replace("-", "").concat(" ..."));
        holder.onlineStatus.setText(friend.isOnline() ? "online" : "offline");
        if (friend.isOnline()) holder.onlineStatus.setTextColor(Constants.ONLINE_STATUS_TEXT_COLOR);
        else holder.onlineStatus.setTextColor(Constants.OFFLINE_STATUS_TEXT_COLOR);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showFriendProfile();
            }
        });
    }

}
