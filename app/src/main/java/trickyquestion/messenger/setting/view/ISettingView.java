package trickyquestion.messenger.setting.view;

import android.content.Context;

public interface ISettingView {
    void goBack();
    Context getContext();
    void showToast(String text);
}
