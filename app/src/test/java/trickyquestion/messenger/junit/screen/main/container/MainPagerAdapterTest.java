package trickyquestion.messenger.junit.screen.main.container;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.login.sign_up.SignUpFragment;
import trickyquestion.messenger.screen.main.container.implementation.MainPagerAdapter;
import trickyquestion.messenger.screen.main.tabs.friends.ui.FriendsFragment;
import trickyquestion.messenger.screen.main.tabs.messages.ui.MessagesFragment;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MainPagerAdapterTest {

    @Mock Context context;
    @Mock Resources resources;
    @Mock FragmentManager fm;
    MainPagerAdapter pagerAdapter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifSizePageAndTitlesDifferent_shouldThrowException() {
        final List<Fragment> pages = Arrays.asList(
                new FriendsFragment(), new MessagesFragment(), new SignUpFragment());
        final List<String> titles = Arrays.asList("Friends", "Messages");
        pagerAdapter = new MainPagerAdapter(fm, pages, titles);
    }

    @Test
    public void createAdapter_shouldContainsValidData() {
        final List<Fragment> pages = Arrays.asList(new FriendsFragment(), new MessagesFragment());
        final List<String> titles = Arrays.asList("Friends", "Messages");
        pagerAdapter = new MainPagerAdapter(fm, pages, titles);
        assertEquals(pagerAdapter.getCount(), pages.size());
        for (int i = 0; i < pages.size(); i++) {
            assertEquals(pages.get(i), pagerAdapter.getItem(i));
            assertEquals(titles.get(i), pagerAdapter.getPageTitle(i));
        }
    }

    @Test
    public void addFragmentInAdapter_shouldContainsValidData() {
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.messages)).thenReturn("Messages");
        pagerAdapter = new MainPagerAdapter(fm, context);

        final FriendsFragment friendsFragment = FriendsFragment.newInstance();
        final MessagesFragment messagesFragment = MessagesFragment.newInstance();

        pagerAdapter.addFragment(friendsFragment, "Friends");
        pagerAdapter.addFragment(messagesFragment, R.string.messages);
        assertEquals(pagerAdapter.getCount(), 2);
        assertEquals(friendsFragment, pagerAdapter.getItem(0));
        assertEquals(messagesFragment, pagerAdapter.getItem(1));
        assertEquals("Friends", pagerAdapter.getPageTitle(0));
        assertEquals("Messages", pagerAdapter.getPageTitle(1));
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
