package trickyquestion.messenger.rooblectric;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.annotation.Config;

import trickyquestion.messenger.BuildConfig;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
@Config(constants = BuildConfig.class, sdk = 17, application = MockApplication.class, manifest = "src/main/AndroidManifest.xml")
public class RobolectricTest {

    @Mock Context context;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(context.getPackageCodePath()).thenReturn("Hello");
    }

    @After
    public void after() {
        Mockito.validateMockitoUsage();
        log(LINE, Color.BLUE);
    }

    @Test
    public void test() {
        passed("test");
    }

    private void passed(final String text) {
        log(text + "  ===> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}