package trickyquestion.messenger.main_screen.main_tabs_content.content_presenter.Messages;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.main_screen.main_tabs_content.content_adapter.Holders.MessageViewHolder;
import trickyquestion.messenger.main_screen.main_tabs_content.content_view.Messages.IMessageView;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Message;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.MessagesRepository;
import trickyquestion.messenger.R;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.event_bus_pojo.AddFriendEvent;
import trickyquestion.messenger.util.event_bus_pojo.SendMessageEvent;

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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setRefreshing(false);
                    wasRefreshStarted = false;
                }
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
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wasRefreshStarted = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setRefreshing(false);
                        wasRefreshStarted = false;
                        updateMessageList();
                    }
                }, 1000);
            }
        };
    }

    @Override
    public int getProgressBackgroundColor() {
        return view.getFragmentContext().getResources().getColor(R.color.colorWhite);
    }

    @Override
    public int[] getSchemeColors() {
        return new int[] {
                view.getFragmentContext().getResources().getColor(R.color.colorPrimaryGreen)
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
        holder.message.setText(message.getLastMessage());
        holder.name.setText(message.getNameSender());
        holder.time.setText(message.getTime());
        if (!message.wasRead()) holder.message.setBackgroundColor(Constants.WAS_READ_MESSAGE_BACKGROUND);
        else holder.message.setBackgroundColor(Color.TRANSPARENT);
        holder.image.setOnClickListener(v -> view.showFriendProfile(message.getNameSender(), true));

        holder.itemView.setOnClickListener(v -> view.showChatActivity(message));
    }

    public void onEvent(SendMessageEvent event){
        updateMessageList();
    }

    private void updateMessageList() {
        this.messageList = MessagesRepository.getMessages();
        this.view.notifyDataSetChanged();
    }
}
