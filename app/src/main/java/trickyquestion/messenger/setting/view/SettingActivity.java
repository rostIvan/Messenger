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
    private ExpandableAdapter adapter;

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    public void customizeToolbar() {
        toolbar.setTitle(R.string.action_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(presenter.onBackPressed());
    }

    public void customizeRecycler() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_setting_expanded);
        adapter = new ExpandableAdapter(this, getParents());
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
        return presenter.getParents();
    }
}
