package trickyquestion.messenger.setting.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;
import trickyquestion.messenger.setting.expand_list.ExpandableAdapter;
import trickyquestion.messenger.setting.presenter.ISettingPresenter;
import trickyquestion.messenger.setting.presenter.SettingPresenter;

public class SettingActivity extends AppCompatActivity implements ISettingView {

    @BindView(R.id.setting_toolbar)
    Toolbar toolbar;
    @BindView(R.id.setting_login_name)
    TextView login;
    @BindView(R.id.setting_id)
    TextView id;

    private ExpandableAdapter adapter;

    private ISettingPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_menu);
        ButterKnife.bind(this);
        if (presenter == null) presenter = new SettingPresenter(this);
        customizeToolbar();
        setUserDate();
        customizeRecycler();
    }

    private void setUserDate() {
        login.setText(presenter.getUserName());
        id.setText(presenter.getUserId());
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.translate_top_side, R.anim.translate_bottom_side);
        overridePendingTransition(R.anim.alpha_to_one, R.anim.translate_bottom_side);
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
        adapter = new ExpandableAdapter(presenter, getParents());
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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private List<ParentObject> getParents() {
        return presenter.getParents();
    }
}
