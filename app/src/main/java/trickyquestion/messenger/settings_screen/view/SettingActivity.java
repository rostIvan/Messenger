package trickyquestion.messenger.settings_screen.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;
import trickyquestion.messenger.settings_screen.expand_list.adapter.ExpandableAdapter;
import trickyquestion.messenger.settings_screen.presenter.ISettingPresenter;
import trickyquestion.messenger.settings_screen.presenter.SettingPresenter;
import trickyquestion.messenger.settings_screen.view.dialogs.ChangeLoginDialog;
import trickyquestion.messenger.settings_screen.view.dialogs.ChangePasswordDialog;
import trickyquestion.messenger.settings_screen.view.dialogs.IChangeDialog;

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
        setContentView(R.layout.activity_setting_menu);
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
    public void showChangeLoginDialog() {
        final IChangeDialog dialog = new ChangeLoginDialog();
        dialog.setOnPositiveClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.setNewLogin(dialog.getEnteredText());
            }
        });
        dialog.setOnNegativeClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), "change login");
    }

    @Override
    public void showChangePasswordDialog() {
        final IChangeDialog dialog = new ChangePasswordDialog();
        dialog.setOnPositiveClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.setNewPassword(dialog.getEnteredText());
            }
        });
        dialog.setOnNegativeClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), "change password");
    }

    @Override
    public void setNewLoginText(final String loginText) {
        login.setText(loginText);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private List<ParentObject> getParents() {
        return presenter.getParents();
    }
}
