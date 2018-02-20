package trickyquestion.messenger.screen.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import trickyquestion.messenger.util.android.preference.AuthPreference;
import trickyquestion.messenger.util.android.view.ViewIdGenerator;

public abstract class SingleFragmentActivity extends FragmentActivity {

    public abstract Fragment createFragment();
    private AuthPreference authPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        final FrameLayout frame = getContainer(this);
        setContentView(frame);
        updateContentInFrame(frame);
    }

    private void init() { authPreference = new AuthPreference(this); }

    private FrameLayout getContainer(final Context context) {
        final FrameLayout container = new FrameLayout(context);
        container.setId(ViewIdGenerator.generateViewId());
        return container;
    }

    private void updateContentInFrame(final FrameLayout layout) {
        final Fragment fragment = createFragment();
        getSupportFragmentManager().beginTransaction()
                .add(layout.getId(), fragment)
                .commit();
    }
    protected AuthPreference getAuthPreference() { return authPreference; }
}

