package trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Friends;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.Holders.FriendViewHolder;
import trickyquestion.messenger.main_screen.main_tabs_content.content_view.Friends.IFriendsView;
import trickyquestion.messenger.main_screen.main_tabs_content.interactors.FriendListInteractor;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.R;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.util.animation.ItemAlphaAnimator;
import trickyquestion.messenger.util.Constants;

public class FriendPresenter implements IFriendPresenter {

    private IFriendsView view;
    private List<Friend> friendList;
    private static boolean profileWasOpened;
    private String searchQuery;

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
    public void onResume() {
        updateFriendList();
        view.notifyRecyclerDataChange();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState == null) return;
        searchQuery = savedInstanceState.getString("svQuery");
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        profileWasOpened = view.isFriendProfileOpen();
        view.dismissPhotoDialog();
        if (view.getSearchQuery() != null && !view.getSearchQuery().isEmpty())
            outState.putString("svQuery", view.getSearchQuery());
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
    public SearchView.OnCloseListener onCloseSearchViewListener() {
        return new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchQuery = null;
                return false;
            }
        };
    }

    @Override
    public String getStackQuery() {
        return searchQuery;
    }

    @Override
    public SearchView.OnQueryTextListener onQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query == null || query.isEmpty()) return false;
                Toast.makeText(view.getFragmentContext(), "Submit: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null) {
                    searchQuery = null;
                    return false;
                }
                searchQuery = newText;
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
        setViewListeners(holder, friend);
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
        holder.onlineStatus.setText(friend.isOnline() ? "online" : "last seen at 4:20");
        if (friend.isOnline()) holder.onlineStatus.setTextColor(Constants.ONLINE_STATUS_TEXT_COLOR);
        else holder.onlineStatus.setTextColor(Constants.OFFLINE_STATUS_TEXT_COLOR);
    }

    private void setViewListeners(final FriendViewHolder holder, final Friend friend) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showChatActivity(friend);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showFriendProfile();
            }
        });
        holder.itemView.setOnCreateContextMenuListener(new onHolderCreateContextMenu(holder, friend));
    }

    private class onHolderCreateContextMenu implements View.OnCreateContextMenuListener {

        private final FriendViewHolder holder;
        private final Friend friend;

        public onHolderCreateContextMenu(FriendViewHolder holder, Friend friend) {
            this.holder = holder;
            this.friend = friend;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(holder.name.getText());
            menu.add(0, 0, 0, "remove");
            menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    FriendsRepository.deleteFriend(friend);
                    updateFriendList();
                    return false;
                }
            });
        }
    }

    private void updateFriendList() {
        this.friendList = FriendListInteractor.getFriends();
        this.view.notifyRecyclerDataChange();
    }
}
