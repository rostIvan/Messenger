package trickyquestion.messenger.account;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;

import trickyquestion.messenger.R;
import trickyquestion.messenger.util.Constants;

public class AccountPopup {
    private final Context mContext;
    private final SharedPreferences preferences;
    private PopupWindow popupWindow;

    public AccountPopup(final Context context) {
        this.mContext = context;
        preferences = context.getSharedPreferences(Constants.PREFERENCE_AUTH_DATA, Context.MODE_PRIVATE);
    }

    public void show() {
        final BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(mContext).inflate(R.layout.account_popup, null);
        final View v = ((Activity) mContext).findViewById(R.id.action_account);
        popupWindow = BubblePopupHelper.create(mContext, bubbleLayout);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, getX(v), getY(v));

        final TextView accountName = (TextView) bubbleLayout.findViewById(R.id.account_name);
        final TextView accountId = (TextView) bubbleLayout.findViewById(R.id.account_id);
        final ImageView changeDataButton = (ImageView) bubbleLayout.findViewById(R.id.change_data_arrow);
        final TextView changeDataLink = (TextView) bubbleLayout.findViewById(R.id.change_data_link);

        accountName.setText(getAccountName());
        accountId.setText(getAccountId());

        changeDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        changeDataLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    private int getX(View view) {
        final int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[0] - 340;
    }
    private int getY(View view) {
        final int[] location = new int[2];
        view.getLocationInWindow(location);
        return view.getHeight() + location[1] - 50;
    }

    private String getAccountName() {
        return preferences.getString(Constants.EXTRA_KEY_AUTH_LOGIN, "__NAN__");
    }

    private String getAccountId() {
        return "id: ".concat(preferences.getString(Constants.EXTRA_KEY_USER_ID, "_NAN_").substring(0, 15).concat(" ..."));
    }
}
