package trickyquestion.messenger.setting.presenter;

import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.setting.expandList.SettingChild;
import trickyquestion.messenger.setting.expandList.SettingParent;
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

    @Override
    public List<ParentObject> getParents() {
        final List<ParentObject> parents = new ArrayList<>();

        final SettingParent parent1 = new SettingParent();
        parent1.setTitle("Account Setting");
        parent1.setImageResourse(R.drawable.ic_person_add);
        final SettingParent parent2 = new SettingParent();
        parent2.setTitle("Notification");
        parent2.setImageResourse(R.drawable.ic_account_circe_dark);
        final SettingParent parent3 = new SettingParent();
        parent3.setTitle("Color managment");
        parent3.setImageResourse(R.drawable.ic_send);

        final SettingChild child1 = new SettingChild("Change name", false, R.drawable.ic_user, false);
        final SettingChild child2 = new SettingChild("Change password", false, R.drawable.ic_arrow, false);
        final SettingChild child3 = new SettingChild("Ask for password", true, R.drawable.ic_add_friend_circle, false);
        final SettingChild child4 = new SettingChild("Log out", false, R.drawable.ic_account_circe_dark, true);

        final SettingChild child5 = new SettingChild("Change A", false, R.drawable.ic_user, false);
        final SettingChild child6 = new SettingChild("Change V", false, R.drawable.ic_arrow, false);
        final SettingChild child7 = new SettingChild("Ask for B", true, R.drawable.ic_add_friend_circle, false);
        final SettingChild child8 = new SettingChild("Log D", false, R.drawable.ic_account_circe_dark, true);

        final SettingChild child9 = new SettingChild("Change 1", false, R.drawable.ic_user, false);
        final SettingChild child10 = new SettingChild("Change 2", false, R.drawable.ic_arrow, false);
        final SettingChild child11 = new SettingChild("Ask for 3", true, R.drawable.ic_add_friend_circle, false);
        final SettingChild child12 = new SettingChild("Log 4", false, R.drawable.ic_account_circe_dark, true);

        final List<Object> childs1 = new ArrayList<>();
        childs1.add(child1);
        childs1.add(child2);
        childs1.add(child3);
        childs1.add(child4);
        final List<Object> childs2 = new ArrayList<>();
        childs2.add(child5);
        childs2.add(child6);
        childs2.add(child7);
        childs2.add(child8);

        final List<Object> childs3 = new ArrayList<>();
        childs3.add(child9);
        childs3.add(child10);
        childs3.add(child11);
        childs3.add(child12);

        parent1.setChildObjectList(childs1);
        parent2.setChildObjectList(childs2);
        parent3.setChildObjectList(childs3);

        parents.add(parent1);
        parents.add(parent2);
        parents.add(parent3);

        return parents;
    }
}
