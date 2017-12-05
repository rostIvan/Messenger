package trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Messages;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.Holders.MessageViewHolder;
import trickyquestion.messenger.main_screen.main_tabs_content.content_view.Messages.IMessageView;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.MessagesRepository;
import trickyquestion.messenger.R;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.event_bus_pojo.ChangeThemeEvent;
import trickyquestion.messenger.util.event_bus_pojo.ChangeMessageDataBaseEvent;
import trickyquestion.messenger.util.preference.ThemePreference;

public class MessagePresenter implements IMessagePresenter {

    private final IMessageView view;
    private List<Message> messageList;
    private static boolean wasRefreshStarted;

    public MessagePresenter(final IMessageView view) {
        this.view = view;
        this.messageList = MessagesRepository.getMessages();
    }


    /** For Fragment View **/
    @Override
    public void onCreateView() {
        EventBus.getDefault().register(this);
        view.showMessageContent();
        view.setupSwipeRefreshLayout();
    }


    @Override
    public void onStart() {
        if (wasRefreshStarted) {
            view.setRefreshing(true);
            view.setToolbarTitle("Updating...");
            new Handler().postDelayed(() -> {
                view.setRefreshing(false);
                wasRefreshStarted = false;
                view.setToolbarTitle(view.getAppName());
            }, 2000);
        }
    }

    @Override
    public void onResume() {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
    }


    /** For Refresh View **/
    @Override
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener() {
        return () -> {
            wasRefreshStarted = true;
            view.setToolbarTitle("Updating...");
            new Handler().postDelayed(() -> {
                wasRefreshStarted = false;
                view.setRefreshing(false);
                updateMessageList();
                view.setToolbarTitle(view.getAppName());
            }, 1000);
        };
    }

    @Override
    public int getProgressBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public int[] getSchemeColors() {
        final Context context = view.getFragmentContext();
        final int primaryColor = new ThemePreference(context).getPrimaryColor();
        return new int[] {
                primaryColor
        };
    }


    /** For Recycler View **/
    @Override
    public MessageViewHolder onCreateView(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messege, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final Message message = messageList.get(position);
        setViewValue(holder, message);
    }


    @Override
    public int getCount() {
        return messageList.size();
    }


    private void setViewValue(final MessageViewHolder holder, final Message message) {
        holder.message.setText(message.getMessage());
        holder.name.setText(message.getNameFriend());
        holder.time.setText(message.getTime());
        if (!message.wasRead()) holder.message.setBackgroundColor(Constants.WAS_READ_MESSAGE_BACKGROUND);
        else holder.message.setBackgroundColor(Color.TRANSPARENT);
        holder.image.setOnClickListener(v -> view.showFriendProfile(message.getNameFriend(), true));
        holder.itemView.setOnClickListener(v -> view.showChatActivity(message));
    }

    public void onEvent(ChangeMessageDataBaseEvent event){
        updateMessageList();
    }

    public void onEvent(ChangeThemeEvent event) {
        view.setupSwipeRefreshLayout();
    }

    private void updateMessageList() {
        this.messageList = MessagesRepository.getMessages();
        this.view.notifyDataSetChanged();
    }
}
