package trickyquestion.messenger.screen.popup_windows;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import trickyquestion.messenger.R;

public class FriendPhotoDialog extends DialogFragment {

    private TextView name;
    private TextView onlineStatus;
    private ImageView photo;

    public static FriendPhotoDialog newInstance(Bundle bundle) {
        final FriendPhotoDialog fragment = new FriendPhotoDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view =View.inflate(getActivity(), R.layout.dialog_photo, null);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(view);
        initViews(view);
        setName(getArguments().getString("name"));
        setOnline(getArguments().getBoolean("online"));
        return dialog.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initViews(View view) {
        name  = (TextView) view.findViewById(R.id.dialog_friend_name);
        onlineStatus  = (TextView) view.findViewById(R.id.dialog_friend_online_status);
        photo = (ImageView) view.findViewById(R.id.dialog_photo_friend);
    }

    public void setName(final String friendName) {
        name.setText(friendName);
    }

    public void setOnline(final boolean online) {
        onlineStatus.setText(online ? "online" : "offline");
        if (online) onlineStatus.setTextColor(Color.GREEN);
        else onlineStatus.setTextColor(Color.RED);
    }
}

