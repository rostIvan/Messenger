package trickyquestion.messenger.add_friend_screen.view;


public interface IAddFriendView {
    void showFriendsItems();

    void notifyRecyclerDataChange();

    void notifyRecyclerItemRemove(int item);
}
