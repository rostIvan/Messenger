package trickyquestion.messenger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class FriendRepositoryTest {
    private final Context context = InstrumentationRegistry.getContext();
    private final FriendRepository repository = new FriendRepository();

    @Before
    public void before() {
        Realm.init(context);
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
        repository.save(new Friend("jack", UUID.randomUUID(), true));
        repository.save(new Friend("john", UUID.randomUUID(), false));
        repository.save(new Friend("nick", UUID.randomUUID(), true));
        final List<Friend> friends = repository.findAll();
        assertEquals(friends.size(), 3);
    }

    @Test
    public void delete_isCorrect() {
        final Friend john = new Friend("john", UUID.randomUUID(), true);
        final Friend ivan = new Friend("ivan", UUID.randomUUID(), true);
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
        final List<Friend> friends = Arrays.asList(new Friend("liza", UUID.randomUUID(), true),
                new Friend("kate", UUID.randomUUID(), true),
                new Friend("helen", UUID.randomUUID(), true),
                new Friend("emma", UUID.randomUUID(), true));
        repository.saveAll(friends);
        final List<Friend> all = repository.findAll();
        assertEquals(all.size(), 4);
    }

    @Test
    public void deleteIterable_isCorrect() {
        final List<Friend> friends = Arrays.asList(new Friend("julia", UUID.randomUUID(), true),
                new Friend("jake", UUID.randomUUID(), true),
                new Friend("alex", UUID.randomUUID(), true));
        repository.saveAll(friends);
        final List<Friend> all = repository.findAll();
        assertEquals(all.size(), 3);
        repository.deleteAll(friends);
    }

    @Test
    public void deleteTable_isCorrect() {
        repository.save(new Friend("jack", UUID.randomUUID(), true));
        repository.save(new Friend("john", UUID.randomUUID(), false));
        final List<Friend> all = repository.findAll();
        assertEquals(all.size(), 2);
        repository.deleteAll();
        assertEquals(all.size(), 0);
    }

    @Test
    public void findFirst_shouldReturnFirstElement() {
        final List<Friend> friends = Arrays.asList(new Friend("endy", UUID.randomUUID(), true),
                new Friend("jake", UUID.randomUUID(), true),
                new Friend("alex", UUID.randomUUID(), true));
        repository.saveAll(friends);
        final Friend first = repository.first();
        assertNotNull(first);
        assertEquals(first, friends.get(0));
    }

    @Test
    public void findLast_shouldReturnLastElement() {
        final List<Friend> friends = Arrays.asList(new Friend("endy", UUID.randomUUID(), true),
                new Friend("jake", UUID.randomUUID(), true),
                new Friend("alex", UUID.randomUUID(), true));
        repository.saveAll(friends);
        final Friend first = repository.last();
        assertNotNull(first);
        assertEquals(first, friends.get(friends.size() - 1));
    }

    @Test
    public void count_shouldReturnCountOfObjects() {
        final List<Friend> friends = Arrays.asList(new Friend("endy", UUID.randomUUID(), true),
                new Friend("jake", UUID.randomUUID(), true),
                new Friend("alex", UUID.randomUUID(), true));
        repository.saveAll(friends);
        final int count = repository.count();
        assertEquals(count, friends.size());
    }


    @Test
    public void findById_shouldReturnFriend() {
        final UUID id = UUID.randomUUID();
        final Friend saved = new Friend("ricky", id, true);
        repository.save(saved);
        final Friend found = repository.findById(id);
        assertNotNull(found);
        assertEquals(saved, found);
    }

    @Test
    public void findByName_shouldReturnFriendList() {
        final List<Friend> friends = Arrays.asList(new Friend("leyla", UUID.randomUUID(), true),
                new Friend("leyla", UUID.randomUUID(), true),
                new Friend("ricky", UUID.randomUUID(), false));
        repository.saveAll(friends);
        final List<Friend> leylaList = repository.findAllByName("leyla");
        assertEquals(leylaList.size(), 2);
        final List<Friend> rickyList = repository.findAllByName("ricky");
        assertEquals(rickyList.size(), 1);
        final List<Friend> someList = repository.findAllByName("some");
        assertEquals(someList.size(), 0);
    }

    private final String TAG = getClass().getCanonicalName();
}
