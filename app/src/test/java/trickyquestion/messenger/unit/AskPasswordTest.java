package trickyquestion.messenger.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import trickyquestion.messenger.util.Color;
import trickyquestion.messenger.util.Mode;

import static trickyquestion.messenger.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AskPasswordTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
    }

    private void passed(final String text) {
        log(text + "  ===> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
