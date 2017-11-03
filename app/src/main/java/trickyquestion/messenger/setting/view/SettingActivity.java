package trickyquestion.messenger.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;
import trickyquestion.messenger.setting.expandList.ExpandableAdapter;
import trickyquestion.messenger.setting.expandList.SettingChild;
import trickyquestion.messenger.setting.expandList.SettingParent;
import trickyquestion.messenger.setting.presenter.ISettingPresenter;
import trickyquestion.messenger.setting.presenter.SettingPresenter;

public class SettingActivity extends AppCompatActivity implements ISettingView{

    @BindView(R.id.setting_toolbar)
    Toolbar toolbar;

    private ISettingPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_menu);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new SettingPresenter(this);
        customizeToolbar();
        customizeRecycler();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.translate_top_side, R.anim.translate_bottom_side);
    }

    public void customizeToolbar() {
        toolbar.setTitle(R.string.action_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(presenter.onBackPressed());
    }

    public void customizeRecycler() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_setting_expanded);
        final ExpandableAdapter adapter = new ExpandableAdapter(this, getParents());
        adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    private List<ParentObject> getParents() {
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

        final SettingChild child1 = new SettingChild("Change name", false, R.drawable.ic_user);
        final SettingChild child2 = new SettingChild("Change password", false, R.drawable.ic_arrow);
        final SettingChild child3 = new SettingChild("Ask for password", true, R.drawable.ic_add_friend_circle);
        final SettingChild child4 = new SettingChild("Log out", false, R.drawable.ic_account_circe_dark);

        final List<Object> childs1 = new ArrayList<>();
        childs1.add(child1);
        childs1.add(child2);
        childs1.add(child3);
        childs1.add(child4);
        final List<Object> childs2 = new ArrayList<>();
        childs2.add(child1);
        childs2.add(child2);
        childs2.add(child3);

        parent1.setChildObjectList(childs1);
        parent2.setChildObjectList(childs2);
        parent3.setChildObjectList(childs2);

        parents.add(parent1);
        parents.add(parent2);
        parents.add(parent3);

        return parents;
    }
}
