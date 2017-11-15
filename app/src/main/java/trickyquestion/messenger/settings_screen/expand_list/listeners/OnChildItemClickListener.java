package trickyquestion.messenger.settings_screen.expand_list.listeners;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import trickyquestion.messenger.R;
import trickyquestion.messenger.settings_screen.expand_list.model.SettingChild;
import trickyquestion.messenger.settings_screen.presenter.ISettingPresenter;

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
            case "Standard" :{
                presenter.setThemePrimaryColor(R.color.colorPrimaryGreen);
                Toast.makeText(presenter.getView().getContext(), "Theme changed: " + "Standard", Toast.LENGTH_SHORT).show();
                break;
            }
            case "Red" :{
                presenter.setThemePrimaryColor(R.color.colorAccent);
                Toast.makeText(presenter.getView().getContext(), "Theme changed: " + "Red", Toast.LENGTH_SHORT).show();
                break;
            }
            case "Orange" :{
                presenter.setThemePrimaryColor(R.color.colorOrange);
                Toast.makeText(presenter.getView().getContext(), "Theme changed: " + "Orange", Toast.LENGTH_SHORT).show();
                break;
            }
            case "Black" :{
                presenter.setThemePrimaryColor(R.color.colorBlack);
                Toast.makeText(presenter.getView().getContext(),"Theme changed: " + "Black", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
