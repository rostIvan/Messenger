package trickyquestion.messenger.junit.p2p_protocol.objects;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.p2p_protocol.objects.OHost;
import trickyquestion.messenger.util.Constants;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.powermock.api.mockito.PowerMockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;
import static trickyquestion.messenger.util.Constants.EXTRA_KEY_AUTH_LOGIN;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OHostTest {

    @Mock Context context;
    @Mock SharedPreferences sharedPreferences;
    OHost oHost;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(context.getSharedPreferences(any(), anyByte())).thenReturn(sharedPreferences);
        oHost = new OHost(context);
    }

    @Test
    public void test() {

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
