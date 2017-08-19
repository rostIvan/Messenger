package trickyquestion.messenger.MainScreen.Presenter;

import android.view.View;
import android.widget.Toast;

import trickyquestion.messenger.MainScreen.View.IMainView;

public class MainPresenter implements IMainPresenter{
    private final IMainView view;

    public MainPresenter(final IMainView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.customizeToolbar();
        view.setupListeners();
    }

    @Override
    public View.OnClickListener onNavigationButtonPressed() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.goBack();
            }
        };
    }

}
