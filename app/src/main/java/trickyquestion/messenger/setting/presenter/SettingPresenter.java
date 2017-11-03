package trickyquestion.messenger.setting.presenter;

import android.view.View;

import trickyquestion.messenger.setting.view.ISettingView;
import trickyquestion.messenger.setting.view.SettingActivity;

public class SettingPresenter implements ISettingPresenter {
    private final ISettingView view;

    public SettingPresenter(ISettingView view) {
        this.view = view;
    }

    @Override
    public View.OnClickListener onBackPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.goBack();
            }
        };
    }
}
