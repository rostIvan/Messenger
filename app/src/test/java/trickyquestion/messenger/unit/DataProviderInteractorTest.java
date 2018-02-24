package trickyquestion.messenger.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import trickyquestion.messenger.buisness.DataProviderInteractor;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend;
import trickyquestion.messenger.util.Color;
import trickyquestion.messenger.util.LoggerKt;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;
import static trickyquestion.messenger.util.LoggerKt.LINE;
import static trickyquestion.messenger.util.LoggerKt.log;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FriendRepository.class, DataProviderInteractor.class, Realm.class})
public class DataProviderInteractorTest {

    private DataProviderInteractor interactor;
    private FriendRepository repository;

    @Before
    public void before() {
        mockRealm();
        spy(FriendRepository.class);
        repository = FriendRepository.INSTANCE;
        interactor = spy(new DataProviderInteractor());
    }

    @After
    public void after() {
        log(LINE, Color.BLUE);
    }

    @Test
    public void dataProvider_shouldReturnFriendsFromDb() {
        final List<Friend> stubList = getStubList();
        stub(method(FriendRepository.class, "findAll")).toReturn(stubList);
        final List<Friend> friendsFromDb = interactor.getFriendsFromDb();
        friendsFromDb.forEach(LoggerKt::log);
        assertEquals(friendsFromDb, stubList);
    }

    @Test
    public void repository_shouldCallNotifyMethod() {
        final int[] calls = {0};
        suppress(method(FriendRepository.class, "realmQuery"));
        stub(method(FriendRepository.class, "findAll")).toReturn(getStubList());
        interactor.subscribeOnUpdates(friends -> {
            friends.forEach(LoggerKt::log);
            log(LINE);
            calls[0]++;
        }, LoggerKt::log);
        repository.notifyDataChanged();
        assertEquals(calls[0], 1);
        repository.save(new Friend());
        repository.save(new Friend());
        assertEquals(calls[0], 3);
        repository.deleteAll();
        assertEquals(calls[0], 4);
        repository.saveAll(Arrays.asList(new Friend(), new Friend()));
        assertEquals(calls[0], 5);
    }

    private void mockRealm() {
        mockStatic(Realm.class);
        Realm mockRealm = PowerMockito.mock(Realm.class);
        when(Realm.getDefaultInstance()).thenReturn(mockRealm);
    }

    private List<Friend> getStubList() {
        return Arrays.asList(
                new Friend("andy", UUID.randomUUID(), Friend.Status.OFFLINE),
                new Friend("johny", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("eliza", UUID.randomUUID(), Friend.Status.OFFLINE),
                new Friend("emma", UUID.randomUUID(), Friend.Status.ONLINE));
    }
}
