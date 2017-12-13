package trickyquestion.messenger.add_friend_screen.presenter;


import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.R;
import trickyquestion.messenger.add_friend_screen.adapter.AddFriendViewHolder;
import trickyquestion.messenger.add_friend_screen.view.IAddFriendView;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.P2PProtocolConnector;
import trickyquestion.messenger.p2p_protocol.events.EAddFriendConfirmed;
import trickyquestion.messenger.p2p_protocol.events.EAddFriendRejected;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.util.event_bus_pojo.ChangeThemeEvent;
import trickyquestion.messenger.util.event_bus_pojo.ChangeUserList;
import trickyquestion.messenger.util.preference.ThemePreference;
import trickyquestion.messenger.util.temp_impl.FriendsGetter;

public class AddFriendPresenter implements IAddFriendPresenter {

    private final IAddFriendView view;
    private List<IUser> users;

    public AddFriendPresenter(final IAddFriendView view) {
        this.view = view;
        this.users = FriendsGetter.getUsers();
    }

    @Override
    public void onCreate() {
        view.customizeTheme();
        view.customizeToolbar();
        view.showFriendsItems();
        EventBus.getDefault().register(this);
    }

    @Override
    public View.OnClickListener onNavigationButtonPressed() {
        return v -> {
            view.goBack();
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
        final IUser user = users.get(position);
        setViewValue(holder, user);
        setupListeners(holder, user);
    }


    @Override
    public int getCount() {
        return users.size();
    }


    @Override
    public void onProgressTimerStart() {
        view.showProgressBar();
        view.showToast("Waiting for user answer...");
    }

    @Override
    public void onProgressTimer() {
        // possibly useful
    }

    @Override
    public void onProgressTimerFinished() {
        view.hideProgressBar();
        view.showToast("Adding user not perform");
    }

    @Override
    public void onCancelTimer() {
        // possibly useful
        view.hideProgressBar();
    }

    @Override
    public void onAlertPositiveButtonPressed(IUser user) {
        view.startTimer();
//        FriendsRepository.addFriend(new Friend(user.getName(), user.getID(), true));
//        P2PProtocolService.LocalBinder binder = new P2PProtocolService().getBinder();
//        binder.SendFriendReq(user);
        P2PProtocolConnector.ProtocolInterface().SendFriendReq(user);
    }

    @Override
    public boolean onRefreshItemClick(final MenuItem menuItem) {
        menuItem.setActionView(view.getProgressView());
        new Handler().postDelayed(() -> {
            view.runOnActivityUiThread(this::updateFriendList);
            menuItem.setActionView(null);
        }, 1_500);
        return true;
    }

    private void setViewValue(final AddFriendViewHolder holder, final IUser user) {
        holder.name.setText(user.getName());
        holder.id.setText(user.getID().toString().substring(0, 25).concat(" ..."));
    }

    private void setupListeners(final AddFriendViewHolder holder, final IUser user) {
        holder.itemView.setOnTouchListener(new OnHolderItemTouchListener(holder));
        holder.buttonAddFriend.setOnTouchListener(new OnHolderItemTouchListener(holder));
        holder.itemView.setOnClickListener(new OnHolderItemClickListener(holder, user));
        holder.buttonAddFriend.setOnClickListener(new OnHolderItemClickListener(holder, user));
    }

    /** Here is located Holder Listeners **/

    private class OnHolderItemClickListener implements View.OnClickListener {
        private final AddFriendViewHolder holder;
        private final IUser user;

        private OnHolderItemClickListener(final AddFriendViewHolder holder, final IUser user) {
            this.holder = holder;
            this.user = user;
        }

        @Override
        public void onClick(View v) {
            view.showAddFriendAlertDialog(user);
            final Friend friend = new Friend(
                    user.getName(),
                    user.getID(),
                    null,
                    true
            );
            FriendsRepository.addFriend(friend);
            //view.cancelTimer();
            view.showToast("User: " + user.getName() + " add to your users");
//            addFriend(holder, user);
        }
    }

    private class OnHolderItemTouchListener implements View.OnTouchListener {
        final AddFriendViewHolder holder;

        private OnHolderItemTouchListener(final AddFriendViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {
            final ThemePreference themePreference = new ThemePreference(v.getContext());
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    holder.itemView.setBackgroundColor(themePreference.getPrimaryColor()); break;
                case MotionEvent.ACTION_UP :
                    holder.itemView.setBackgroundColor(Color.WHITE); break;
                case MotionEvent.ACTION_CANCEL :
                    holder.itemView.setBackgroundColor(Color.WHITE); break;
            }
            return false;
        }
    }

    public void onEvent(ChangeUserList event){
        this.view.runOnActivityUiThread(this::updateFriendList);
    }

    public void onEvent(final ENetworkStateChanged event) {
        this.view.runOnActivityUiThread( () ->  onNetworkChanged(event));
    }

    public void onEvent(ChangeThemeEvent event) {
        view.customizeTheme();
    }

    public void onEvent(EAddFriendConfirmed event) {
        final Friend friend = new Friend(
                event.getFriend().getName(),
                event.getFriend().getID(),
                null,
                true
        );
        FriendsRepository.addFriend(friend);
        view.cancelTimer();
        view.showToast("User: " + event.getFriend().getName() + " add to your users");
    }

    public void onEvent(EAddFriendRejected event) {
        view.showToast("User: " + event.getFriend().getName() + " reject your request");
    }

    private void updateFriendList() {
        this.users = FriendsGetter.getUsers();
        this.view.notifyRecyclerDataChange();
    }

    private void onNetworkChanged(ENetworkStateChanged event) {
        if ( event.getNewNetworkState() == NetworkState.INACTIVE ) {
            users.clear();
            this.view.notifyRecyclerDataChange();
        }
        else if (event.getNewNetworkState() == NetworkState.ACTIVE) {
            updateFriendList();
        }
    }
}
