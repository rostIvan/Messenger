package trickyquestion.messenger.add_friend_screen.presenter;


import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import trickyquestion.messenger.R;
import trickyquestion.messenger.add_friend_screen.adapter.AddFriendViewHolder;
import trickyquestion.messenger.add_friend_screen.model.IFriend;
import trickyquestion.messenger.add_friend_screen.view.IAddFriendView;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.p2p_protocol.P2PNetwork;
import trickyquestion.messenger.util.event_bus_pojo.ChangeUserList;
import trickyquestion.messenger.util.temp_impl.FriendsGetter;

public class AddFriendPresenter implements IAddFriendPresenter {

    private final IAddFriendView view;
    private List<IFriend> friends;

    public AddFriendPresenter(final IAddFriendView view) {
        this.view = view;
        this.friends = FriendsGetter.getFriends();
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.showFriendsItems();
        EventBus.getDefault().register(this);
    }

    @Override
    public View.OnClickListener onNavigationButtonPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!view.isSearchViewIconified()) view.setSearchViewIconified(true);
                else view.goBack();
            }
        };
    }

    @Override
    public AddFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_add_friend, parent, false
        );
        return new AddFriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddFriendViewHolder holder, int position) {
        final IFriend friend = friends.get(position);
        setViewValue(holder, friend);
        setupListeners(holder, friend);
    }


    @Override
    public int getCount() {
        return friends.size();
    }


    private void setViewValue(final AddFriendViewHolder holder, final IFriend friend) {
        holder.name.setText(friend.getName());
        holder.id.setText(friend.getId().toString().substring(0, 25).concat(" ..."));
        if (friend.getImage() != null) {
            holder.image.setImageDrawable(friend.getImage().getDrawable());
        }
    }

    private void setupListeners(final AddFriendViewHolder holder, final IFriend friend) {

        holder.itemView.setOnTouchListener(new OnHolderItemTouchListener(holder));
        holder.buttonAddFriend.setOnTouchListener(new OnHolderItemTouchListener(holder));

        holder.itemView.setOnClickListener(new OnHolderItemClickListener(holder, friend));
        holder.buttonAddFriend.setOnClickListener(new OnHolderItemClickListener(holder, friend));
    }

    /** Here is located Holder Listeners **/

    private class OnHolderItemClickListener implements View.OnClickListener {
        private final AddFriendViewHolder holder;
        private final IFriend friend;

        private OnHolderItemClickListener(final AddFriendViewHolder holder, final IFriend friend) {
            this.holder = holder;
            this.friend = friend;
        }

        @Override
        public void onClick(View view) {
            addFriend(holder, friend);
        }


        private void addFriend(final AddFriendViewHolder holder, final IFriend friend) {
            final Friend newFriend = new Friend(friend.getName(), friend.getId(),  null, true);
            FriendsRepository.addFriend(newFriend);
            friends.remove(holder.getAdapterPosition());
            view.notifyRecyclerDataChange();
        }

    }

    private class OnHolderItemTouchListener implements View.OnTouchListener {
        final AddFriendViewHolder holder;

        private OnHolderItemTouchListener(final AddFriendViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN : {
                    holder.itemView.setBackgroundColor(v.getResources().getColor(R.color.colorPrimaryGreen));
                    break;
                }
                case MotionEvent.ACTION_UP : {
                    holder.itemView.setBackgroundColor(v.getResources().getColor(R.color.colorWhite));
                    break;
                }
                case MotionEvent.ACTION_CANCEL : {
                    holder.itemView.setBackgroundColor(v.getResources().getColor(R.color.colorWhite));
                    break;
                }
            }
            return false;
        }
    }


    public void onEvent(ChangeUserList event){
        updateFriendList();
    }
    private void updateFriendList() {
        this.friends = FriendsGetter.getFriends();
        this.view.notifyRecyclerDataChange();
    }
}
