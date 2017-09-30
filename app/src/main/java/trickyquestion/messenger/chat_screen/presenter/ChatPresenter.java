package trickyquestion.messenger.chat_screen.presenter;


import android.view.View;

import trickyquestion.messenger.chat_screen.view.IChatView;

public class ChatPresenter implements IChatPresenter {
    private final IChatView view;

    public ChatPresenter(final IChatView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
    }

    @Override
    public View.OnClickListener onNavigationButtonPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.goBack();
            }
        };
    }
}
