package trickyquestion.messenger.screen.popup_windows;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;

import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.settings.view.SettingActivity;
import trickyquestion.messenger.util.android.preference.AuthPreference;
import trickyquestion.messenger.util.android.preference.ThemePreference;

public class AccountPopup {
    private final Context mContext;
    private PopupWindow popupWindow;

    private AuthPreference authPreference;
    private ThemePreference themePreference;

    private TextView accountName;
    private TextView accountId;
    private TextView changeDataLink;
    private ImageButton buttonArrow;

    public AccountPopup(final Context context) {
        this.mContext = context;
        authPreference = new AuthPreference(context);
        themePreference = new ThemePreference(context);
    }

    public void show() {
        if (mContext == null) throw new RuntimeException("Called window without context");
        @SuppressLint("InflateParams")
        final BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(mContext).inflate(R.layout.popup_account, null);
        final View v = ((Activity) mContext).findViewById(R.id.action_account);
        popupWindow = BubblePopupHelper.create(mContext, bubbleLayout);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, getX(v), getY(v));
        initViews(bubbleLayout);
        setValues();
        customizeTheme();
        setupListeners();
    }


    private void initViews(BubbleLayout bubbleLayout) {
        accountName = (TextView) bubbleLayout.findViewById(R.id.account_name);
        accountId = (TextView) bubbleLayout.findViewById(R.id.account_id);
        changeDataLink = (TextView) bubbleLayout.findViewById(R.id.change_data_link);
        buttonArrow = (ImageButton) bubbleLayout.findViewById(R.id.change_data_arrow);
    }

    private void customizeTheme() {
        changeDataLink.setTextColor(themePreference.getPrimaryColor());
        changeDataLink.setPaintFlags(changeDataLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        buttonArrow.setColorFilter(themePreference.getPrimaryColor());
    }


    private void setValues() {
        accountName.setText(getAccountName());
        accountId.setText(getAccountId());
    }

    private void setupListeners() {
        changeDataLink.setOnClickListener(view -> openSetting());
        buttonArrow.setOnClickListener(view -> openSetting());
    }

    private void openSetting() {
        final Intent i = new Intent(mContext, SettingActivity.class);
        mContext.startActivity(i);
        dismiss();
    }

    public boolean isShowing() {
        return popupWindow != null && popupWindow.isShowing();
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
