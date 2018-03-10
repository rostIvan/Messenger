package trickyquestion.messenger.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.screen.tabs.friends.data.Friend;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class FriendRepositoryTest {
    private final Context context = InstrumentationRegistry.getContext();
    private final FriendRepository repository = FriendRepository.INSTANCE;

    @Before
    public void before() {
        Realm.init(context);
        repository.deleteAll();
    }

    @After
    public void after() {
        repository.deleteAll();
    }

    @Test
    public void objects_notNull() {
        assertNotNull(context);
        assertNotNull(repository);
    }

    @Test
    public void save_isCorrect() {
        repository.save(new Friend("jack", UUID.randomUUID(), Friend.Status.ONLINE));
        repository.save(new Friend("john", UUID.randomUUID(), Friend.Status.OFFLINE));
        repository.save(new Friend("nick", UUID.randomUUID(), Friend.Status.ONLINE));
        final List<Friend> friends = repository.findAll();
        assertEquals(friends.size(), 3);
    }

    @Test
    public void delete_isCorrect() {
        final Friend john = new Friend("john", UUID.randomUUID(), Friend.Status.ONLINE);
        final Friend ivan = new Friend("ivan", UUID.randomUUID(), Friend.Status.ONLINE);
        repository.save(john);
        repository.save(ivan);
        final List<Friend> friends = repository.findAll();
        assertEquals(friends.size(), 2);
        repository.delete(john);
        assertEquals(friends.size(), 1);
        repository.delete(ivan);
        assertEquals(friends.size(), 0);
    }

    @Test
    public void saveIterable_isCorrect() {
        final List<Friend> friends = Arrays.asList(new Friend("liza", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("kate", UUID.randomUUID(), Friend.Status.OFFLINE),
                new Friend("helen", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("emma", UUID.randomUUID(), Friend.Status.OFFLINE));
        repository.saveAll(friends);
        final List<Friend> all = repository.findAll();
        assertEquals(all.size(), 4);
    }

    @Test
    public void deleteIterable_isCorrect() {
        final List<Friend> friends = Arrays.asList(new Friend("julia", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("jake", UUID.randomUUID(), Friend.Status.OFFLINE),
                new Friend("alex", UUID.randomUUID(), Friend.Status.OFFLINE));
        repository.saveAll(friends);
        final List<Friend> all = repository.findAll();
        assertEquals(all.size(), 3);
        repository.deleteAll(friends);
        assertEquals(all.size(), 0);
    }

    @Test
    public void deleteTable_isCorrect() {
        repository.save(new Friend("jack", UUID.randomUUID(), Friend.Status.ONLINE));
        repository.save(new Friend("john", UUID.randomUUID(), Friend.Status.OFFLINE));
        final List<Friend> all = repository.findAll();
        assertEquals(all.size(), 2);
        repository.deleteAll();
        assertEquals(all.size(), 0);
    }

    @Test
    public void findFirst_shouldReturnFirstElement() {
        final List<Friend> friends = Arrays.asList(new Friend("andy", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("jake", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("alex", UUID.randomUUID(), Friend.Status.OFFLINE));
        repository.saveAll(friends);
        final Friend first = repository.first();
        assertNotNull(first);
        assertEquals(first, friends.get(0));
    }

    @Test
    public void findLast_shouldReturnLastElement() {
        final List<Friend> friends = Arrays.asList(new Friend("andy", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("jake", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("alex", UUID.randomUUID(), Friend.Status.OFFLINE));
        repository.saveAll(friends);
        final Friend last = repository.last();
        assertNotNull(last);
        assertEquals(last, friends.get(friends.size() - 1));
    }

    @Test
    public void count_shouldReturnCountOfObjects() {
        final List<Friend> friends = Arrays.asList(new Friend("endy", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("jake", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("alex", UUID.randomUUID(), Friend.Status.ONLINE));
        repository.saveAll(friends);
        final int count = repository.count();
        assertEquals(count, friends.size());
    }

    @Test
    public void isEmpty_shouldReturnBooleanOfRepositoryEmpty() {
        assertTrue(repository.isEmpty());
        repository.save(new Friend("artur", UUID.randomUUID(), Friend.Status.ONLINE));
        assertFalse(repository.isEmpty());
        repository.deleteAll();
        assertTrue(repository.isEmpty());
    }

    @Test
    public void findById_shouldReturnFriend() {
        final UUID id = UUID.randomUUID();
        final Friend saved = new Friend("ricky", id, Friend.Status.ONLINE);
        repository.save(saved);
        final Friend found = repository.findById(id);
        assertNotNull(found);
        assertEquals(saved, found);
    }

    @Test
    public void findByName_shouldReturnFriendList() {
        final List<Friend> friends = Arrays.asList(new Friend("leyla", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("leyla", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("ricky", UUID.randomUUID(), Friend.Status.OFFLINE));
        repository.saveAll(friends);
        final List<Friend> leylaList = repository.findAllByName("leyla");
        assertEquals(leylaList.size(), 2);
        for (Friend friend : leylaList)
            assertEquals(friend.getName(), "leyla");
        final List<Friend> rickyList = repository.findAllByName("ricky");
        assertEquals(rickyList.size(), 1);
        for (Friend friend : rickyList)
            assertEquals(friend.getName(), "ricky");
        final List<Friend> someList = repository.findAllByName("some");
        assertTrue(someList.isEmpty());
    }

    @Test
    public void findAllOnline_shouldReturnOnlineFriends() {
        final List<Friend> friends = Arrays.asList(new Friend("kira", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("allen", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("amanda", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("luk", UUID.randomUUID(), Friend.Status.OFFLINE));
        repository.saveAll(friends);
        final List<Friend> allOnline = repository.findAllOnline();
        assertEquals(allOnline.size(), 3);
        for (Friend friend : allOnline)
            assertTrue(friend.isOnline());
    }

    @Test
    public void findAllOffline_shouldReturnOfflineFriends() {
        final List<Friend> friends = Arrays.asList(new Friend("kelly", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("jack", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("nick", UUID.randomUUID(), Friend.Status.ONLINE),
                new Friend("john", UUID.randomUUID(), Friend.Status.OFFLINE));
        repository.saveAll(friends);
        final List<Friend> allOffline = repository.findAllOffline();
        assertEquals(allOffline.size(), 1);
        for (Friend friend : allOffline)
            assertFalse(friend.isOnline());
    }

    @Test
    public void encryptionKey_shouldSaveProperly() {
        final Friend friend = new Friend("mike", UUID.randomUUID(), Friend.Status.ONLINE);
        final byte[] encryptionKey = {1, 2, 3, 4};
        friend.setEncryptionKey(encryptionKey);
        repository.save(friend);
        final Friend fromDb = repository.findAllByName("mike").get(0);
        final byte[] expectedEncryptionKey = fromDb.getEncryptionKey();
        assertEquals(expectedEncryptionKey.length, encryptionKey.length);
        assertTrue(Arrays.equals(encryptionKey, expectedEncryptionKey));
    }

    @Test
    public void findByEncryptionKey_shouldReturnKey() {
        final byte[] encryptionKey = {1, 2, 3};
        final Friend friend = new Friend("mike", UUID.randomUUID(), Friend.Status.ONLINE, encryptionKey);
        repository.save(friend);
        final Friend fromDb = repository.findByEncryptionKey(encryptionKey);
        assertNotNull(fromDb);
        assertTrue(Arrays.equals(encryptionKey, fromDb.getEncryptionKey()));
    }

    @Test
    public void updateFriendStatus_isCorrect() {
        final Friend friend = new Friend("mike", UUID.randomUUID(), Friend.Status.ONLINE);
        repository.save(friend);
        repository.updateFriendStatus(friend, false);
        assertFalse(friend.isOnline());
        repository.updateFriendStatus(friend, true);
        assertTrue(friend.isOnline());
    }

    private void log(final String text) {
        Log.d(TAG, text);
    }

    private final String TAG = getClass().getCanonicalName();
}
