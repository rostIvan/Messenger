package trickyquestion.messenger.setting.presenter;

import android.content.Context;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import trickyquestion.messenger.setting.view.ISettingView;

public interface ISettingPresenter {
    View.OnClickListener onBackPressed();
    List<ParentObject> getParents();
    ISettingView getView();

    void onChangeNameItemClick();
    void onChangePassItemClick();
    void onAskPassItemClick(boolean askPass);
    void onLogOutItemClick();

    void restartApp(Context context);

    String  getUserName();
    String  getUserId();
}
