package trickyquestion.messenger.setting.presenter;

import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public interface ISettingPresenter {
    View.OnClickListener onBackPressed();
    List<ParentObject> getParents();
}
