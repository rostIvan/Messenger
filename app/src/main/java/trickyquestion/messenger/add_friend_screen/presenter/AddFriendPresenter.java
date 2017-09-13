package trickyquestion.messenger.add_friend_screen.presenter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.add_friend_screen.adapter.AddFriendViewHolder;
import trickyquestion.messenger.add_friend_screen.view.IAddFriendView;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.R;

public class AddFriendPresenter implements IAddFriendPresenter {

    private final IAddFriendView view;
    private List<Friend> friends;

    public AddFriendPresenter(final IAddFriendView view) {
        this.view = view;
        friends = FriendsRepository.createRandom(40);
    }

    @Override
    public void onCreate() {
        view.showFriendsItems();
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
        final Friend friend = friends.get(position);
        setViewValue(holder, friend);
    }

    @Override
    public int getCount() {
        return friends.size();
    }


    private void setViewValue(final AddFriendViewHolder holder, final Friend friend) {
        holder.name.setText(friend.getName());
        holder.id.setText(friend.getId().toString().substring(0, 25).concat(" ..."));
        if (friend.getImage() != null)
            holder.image.setImageDrawable(friend.getImage().getDrawable());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Friend newFriend = new Friend(holder.name.getText().toString(), UUID.randomUUID(),  null, true);
                FriendsRepository.addFriend(newFriend);
                friends.remove(holder.getAdapterPosition());
                view.notifyRecyclerDataChange();
            }
        });
    }
}
