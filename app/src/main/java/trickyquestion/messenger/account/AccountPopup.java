package trickyquestion.messenger.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;

import trickyquestion.messenger.R;
import trickyquestion.messenger.settings_screen.view.SettingActivity;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.preference.AuthPreference;

public class AccountPopup {
    private final Context mContext;
    private PopupWindow popupWindow;

    private AuthPreference authPreference;

    public AccountPopup(final Context context) {
        this.mContext = context;
        authPreference = new AuthPreference(context);
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
        changeDataLink.setPaintFlags(changeDataLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        changeDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting();
            }
        });
        changeDataLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting();
            }
        });
    }

    private void openSetting() {
        final Intent i = new Intent(mContext, SettingActivity.class);
        mContext.startActivity(i);
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
        return authPreference.getAccountLogin();
    }

    private String getAccountId() {
        return authPreference.getAccountId().substring(0, 15).concat(" ...");
    }
}
