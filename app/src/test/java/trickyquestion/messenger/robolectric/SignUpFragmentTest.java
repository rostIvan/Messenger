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
import trickyquestion.messenger.buisness.P2PConnector;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.robolectric.support.MockApplication;
import trickyquestion.messenger.robolectric.support.SupportRobolectricGradleTestRunner;
import trickyquestion.messenger.screen.login.sign_up.SignUpActivity;
import trickyquestion.messenger.screen.login.sign_up.SignUpFragment;
import trickyquestion.messenger.screen.login.sign_up.SignUpViewModel;
import trickyquestion.messenger.ui.activity.ApplicationRouter;
import trickyquestion.messenger.ui.interfaces.BaseRouter;
import trickyquestion.messenger.util.android.preference.AuthPreference;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = 21,
        application = MockApplication.class,
        manifest = "src/main/AndroidManifest.xml")
public class SignUpFragmentTest {
    @Mock private ApplicationRouter router;
    @Mock private AuthPreference authPreference;
    @Mock private P2PConnector p2pConnector;

    private SignUpViewModel signUpViewModel;
    private SignUpFragment signUpFragment;
    private EditText nickField;
    private EditText passField;
    private TextView buttonSignUp;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        initFragment();
        bindView();
    }

    private void initFragment() {
        signUpFragment = new SignUpFragment();
        signUpViewModel = new SignUpViewModel(router, authPreference, p2pConnector);
        signUpFragment.attach(signUpViewModel);
        startFragment(signUpFragment, SignUpActivity.class);
        assertNotNull(signUpFragment);
    }

    private void bindView() {
        final View view = signUpFragment.getView();
        assertNotNull(view);
        nickField = view.findViewById(R.id.nick_field);
        passField = view.findViewById(R.id.pass_field);
        buttonSignUp = view.findViewById(R.id.button_create_account);
        assertNotNull(nickField);
        assertNotNull(passField);
        assertNotNull(buttonSignUp);
    }

    @Ignore("Because robolectric is very unstable")
    @Test
    public void inCaseCorrectInput_shouldCreateAccount() {
        final String login = "Vasya";
        final String password = "12345";
        nickField.setText(login);
        passField.setText(password);
        buttonSignUp.performClick();
        verify(authPreference).setAccountData(login, password);
        verify(authPreference).setAccountId(any());
        verify(authPreference).setUserAuthenticated(true);
        verify(p2pConnector).connect();
        verify(router).openScreen(BaseRouter.Screen.MAIN);
        verify(router).finishActivity();
        ShadowLooper.idleMainLooper();
        assertEquals(ShadowToast.getTextOfLatestToast(), null);
        passed("inCaseCorrectInput");
    }

    @Test
    @Ignore("Because robolectric is very unstable")
    public void inCaseTooShortLogin_shouldShowError() {
        final String login = "V1";
        final String password = "111111";
        nickField.setText(login);
        passField.setText(password);
        buttonSignUp.performClick();
        verify(authPreference, never()).setAccountData(login, password);
        verify(authPreference, never()).setAccountId(any());
        verify(authPreference, never()).setUserAuthenticated(true);
        verify(p2pConnector, never()).connect();
        verify(router, never()).openScreen(BaseRouter.Screen.MAIN);
        verify(router, never()).finishActivity();
        ShadowLooper.idleMainLooper();
        assertEquals(ShadowToast.getTextOfLatestToast(), "Incorrect input");
        passed("inCaseTooShortLogin");
    }

    @Test
    @Ignore("Because robolectric is very unstable")
    public void inCaseIncorrectPassword_shouldShowError() {
        final String login = "Vasya";
        final String password = "01";
        nickField.setText(login);
        passField.setText(password);
        buttonSignUp.performClick();
        verify(authPreference, never()).setAccountData(login, password);
        verify(authPreference, never()).setAccountId(any());
        verify(authPreference, never()).setUserAuthenticated(true);
        verify(p2pConnector, never()).connect();
        verify(router, never()).openScreen(BaseRouter.Screen.MAIN);
        verify(router, never()).finishActivity();
        ShadowLooper.idleMainLooper();
        assertEquals(ShadowToast.getTextOfLatestToast(), "Incorrect input");
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