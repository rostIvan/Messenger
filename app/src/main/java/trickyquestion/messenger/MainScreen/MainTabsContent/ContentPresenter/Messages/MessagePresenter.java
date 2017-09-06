package trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Messages;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.Holders.MessageViewHolder;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Messages.IMessageView;
import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Message;
import trickyquestion.messenger.MainScreen.MainTabsContent.Repository.MessagesRepository;
import trickyquestion.messenger.R;
import trickyquestion.messenger.Util.Constants;

public class MessagePresenter implements IMessagePresenter {
    private final IMessageView view;
    private List<Message> messageList;
    private static boolean WAS_REFRESH_STARTED;

    public MessagePresenter(final IMessageView view) {
        this.view = view;
        messageList = MessagesRepository.getFriends();
    }


    /** For Fragment View **/
    @Override
    public void onCreateView() {
        view.showMessageContent();
        view.setupSwipeRefreshLayout();

        if (WAS_REFRESH_STARTED) {
            view.setRefreshing(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setRefreshing(false);
                    WAS_REFRESH_STARTED = false;
                }
            }, 2000);
        }
    }

    /** For Refresh View **/
    @Override
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WAS_REFRESH_STARTED = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setRefreshing(false);
                        WAS_REFRESH_STARTED = false;
                    }
                }, 4000);
            }
        };
    }

    @Override
    public int getProgressBackgroundColor() {
        return view.getFragmentContext().getResources().getColor(R.color.colorBlack);
    }

    @Override
    public int[] getSchemeColors() {
        return new int[] {
                view.getFragmentContext().getResources().getColor(R.color.colorAccent),
//                view.getFragmentContext().getResources().getColor(R.color.colorRed),
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


    private void setViewValue(MessageViewHolder holder, Message message) {
        holder.message.setText(message.getMessageText());
        holder.name.setText(message.getNameSender());
        holder.time.setText(message.getTime());
        if (message.isWasRead())
            holder.message.setBackgroundColor(Constants.WAS_READ_MESSAGE_BACKGROUND);
    }
}
