package trickyquestion.messenger.robolectric;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.ShadowToast;

import trickyquestion.messenger.BuildConfig;
import trickyquestion.messenger.R;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.robolectric.support.MockApplication;
import trickyquestion.messenger.robolectric.support.SupportRobolectricGradleTestRunner;
import trickyquestion.messenger.screen.login.ask_password.AskPasswordActivity;
import trickyquestion.messenger.screen.login.ask_password.AskPasswordFragment;
import trickyquestion.messenger.screen.login.ask_password.AskPasswordViewModel;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = 21,
        application = MockApplication.class)
public class AskPasswordTest {
    @Mock private ApplicationRouter router;
    @Mock private AuthPreference authPreference;
    private AskPasswordViewModel viewModel;
    private AskPasswordFragment askPasswordFragment;

    private TextView helloText;
    private EditText passField;
    private TextView buttonSignIn;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        stubMethods();
        initFragment();
        bindView();
    }

    private void initFragment() {
        viewModel = new AskPasswordViewModel(router, authPreference);
        askPasswordFragment = new AskPasswordFragment();
        askPasswordFragment.attach(viewModel);
        startFragment(askPasswordFragment, AskPasswordActivity.class);
        assertNotNull(askPasswordFragment);
    }

    private void bindView() {
        final View view = askPasswordFragment.getView();
        assertNotNull(view);
        helloText = view.findViewById(R.id.hello_text);
        passField = view.findViewById(R.id.pass_ask_field);
        buttonSignIn = view.findViewById(R.id.button_sign_in);
        assertNotNull(helloText);
        assertNotNull(passField);
        assertNotNull(buttonSignIn);
    }

    private void stubMethods() {
        when(authPreference.getAccountLogin()).thenReturn("Petya");
        when(authPreference.getAccountPassword()).thenReturn("123456");
    }

    @Ignore("Because robolectric is very unstable")
    @Test
    public void helloText_shouldContainsActualUsername() {
        final String text = helloText.getText().toString();
        assertTrue(text.contains("Hello, Petya"));
        passed("helloText_shouldContainsActualUsername");
    }

    @Ignore("Because robolectric is very unstable")
    @Test
    public void inCaseCorrectPassword_shouldShowMainScreen() {
        passField.setText("123456");
        buttonSignIn.performClick();
        verify(router).openScreen(BaseRouter.Screen.MAIN);
        verify(router).finishActivity();
        ShadowLooper.idleMainLooper();
        assertEquals(ShadowToast.getTextOfLatestToast(), null);
        passed("inCaseCorrectPassword");
    }

    @Ignore("Because robolectric is very unstable")
    @Test
    public void inCaseIncorrectPassword_shouldShowError() {
        passField.setText("901234");
        buttonSignIn.performClick();
        verify(router, never()).openScreen(BaseRouter.Screen.MAIN);
        verify(router, never()).finishActivity();
        ShadowLooper.idleMainLooper();
        assertEquals(ShadowToast.getTextOfLatestToast(), "Incorrect password, try again!");
        passed("inCaseIncorrectPassword");
    }

    @After
    public void after() {
        Mockito.validateMockitoUsage();
        log(LINE, Color.BLUE);
    }

    private void passed(final String text) {
        log(text + "  ===> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
