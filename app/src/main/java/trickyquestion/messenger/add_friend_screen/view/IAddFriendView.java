package trickyquestion.messenger.add_friend_screen.view;


public interface IAddFriendView {
    void customizeToolbar();

    void goBack();

    void showFriendsItems();

    void notifyRecyclerDataChange();

    boolean isSearchViewIconified();

    void setSearchViewIconified(boolean iconified);
}
