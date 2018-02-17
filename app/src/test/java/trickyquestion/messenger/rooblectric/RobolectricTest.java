package trickyquestion.messenger.rooblectric;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import trickyquestion.messenger.BuildConfig;
import trickyquestion.messenger.util.Color;
import trickyquestion.messenger.util.Mode;

import static trickyquestion.messenger.util.LoggerKt.LINE;
import static trickyquestion.messenger.util.LoggerKt.log;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 17, application = MockApplication.class)
public class RobolectricTest {

    private final Context context = RuntimeEnvironment.application.getApplicationContext();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        passed("test");
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