package trickyquestion.messenger.setting.expand_list;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import trickyquestion.messenger.R;
import trickyquestion.messenger.setting.presenter.ISettingPresenter;

public class OnChildItemClickListener implements View.OnClickListener {

    private final SettingChild child;
    private final ISettingPresenter presenter;

    public OnChildItemClickListener(final ISettingPresenter presenter, final SettingChild child) {
        this.presenter = presenter;
        this.child = child;
    }

    @Override
    public void onClick(View v) {
        switch (child.getTitle()) {
            case "Change name" :  {
                presenter.onChangeNameItemClick();
                break;
            }
            case "Change password" :  {
                presenter.onChangePassItemClick();
                break;
            }
            case "Ask for password" :  {
                final CheckBox askPass = (CheckBox) v.findViewById(R.id.child_list_item_check_box);
                if (askPass.isChecked()) {
                    askPass.setChecked(false);
                    presenter.onAskPassItemClick(false);
                }
                else {
                    askPass.setChecked(true);
                    presenter.onAskPassItemClick(true);
                }
                break;
            }
            case "Log out" :  {
                presenter.onLogOutItemClick();
                break;
            }
        }
    }
}
