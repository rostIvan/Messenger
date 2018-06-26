package trickyquestion.messenger.junit.util.java.maping;

import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.data.util.Color;
import trickyquestion.messenger.data.util.Mode;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.messages.data.Message;
import trickyquestion.messenger.util.java.maping.Filter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static trickyquestion.messenger.data.util.LoggerKt.LINE;
import static trickyquestion.messenger.data.util.LoggerKt.log;

/**
 * Created by Nadiia Bogoslavets and Basil Polych on 24.02.2018.
 */

public class FilterTest {

    @Test(expected = IllegalArgumentException.class)
    public void ifSearchValueNull_shouldThrowException() {
        passed("ifSearchValueNull_shouldThrowException");
        final List<Friend> friends = Arrays.asList(new Friend(), new Friend(), new Friend());
        final List<Friend> filtered = Filter.friend(friends, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void _ifSearchValueNull_shouldThrowException() {
        passed("_ifSearchValueNull_shouldThrowException");
        final List<Message> messages = Arrays.asList(new Message(), new Message(), new Message());
        final List<Message> filtered = Filter.messages(messages, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifUsingUnsupportedObject_shouldThrowException() {
        passed("ifUsingUnsupportedObject_shouldThrowException");
        final List<String> unsupported = Arrays.asList("1", "2");
        final List<String> filtered = Filter.find(unsupported, "1");
    }

    @Test
    public void ifSearchValueEmpty_shouldReturnAllFriend() {
        final List<Friend> friends = Arrays.asList(
                new Friend("vasya", UUID.randomUUID()),
                new Friend("petya", UUID.randomUUID()),
                new Friend("kolya", UUID.randomUUID()));
        final List<Friend> filtered = Filter.friend(friends, "");
        assertEquals(filtered.size(), 3);
        passed("ifSearchValueEmpty_shouldReturnAllFriend");
    }

    @Test
    public void filter_shouldReturnFilteredFriends() {
        final List<Friend> friends = Arrays.asList(
                new Friend("vasya123", UUID.randomUUID()),
                new Friend("vasya95", UUID.randomUUID()),
                new Friend("petya", UUID.randomUUID()),
                new Friend("___petya", UUID.randomUUID()),
                new Friend("petya3333", UUID.randomUUID()),
                new Friend("kolya", UUID.randomUUID()));
        final String query1 = "vasya";
        final List<Friend> filtered1 = Filter.friend(friends, query1);
        assertEquals(filtered1.size(), 2);
        filtered1.forEach(f ->
                assertTrue(f.getName().toLowerCase().contains(query1.toLowerCase())));

        final String query2 = "petya";
        final List<Friend> filtered2 = Filter.friend(friends, query2);
        assertEquals(filtered2.size(), 3);
        filtered2.forEach(f ->
                assertTrue(f.getName().toLowerCase().contains(query2.toLowerCase())));
        passed("filter_shouldReturnFilteredFriends");
    }

    @Test
    public void filter_shouldReturnFilteredMessages() {
        final Friend friend1 = new Friend("Petya", UUID.randomUUID());
        final Friend friend2 = new Friend("Nick", UUID.randomUUID());
        final List<Message> messages = Arrays.asList(
                new Message("it's a joke", friend1, null),
                new Message("good joke", friend2, null),
                new Message("cool", friend1, null),
                new Message("great", friend1, null),
                new Message("give me some example", friend2, null));

        final String query1 = "joke";
        final List<Message> filtered1 = Filter.messages(messages, query1);
        assertEquals(filtered1.size(), 2);
        filtered1.forEach(m ->
                assertTrue(m.getText().toLowerCase().contains(query1.toLowerCase())));

        final String query2 = "cool";
        final List<Message> filtered2 = Filter.messages(messages, query2);
        assertEquals(filtered2.size(), 1);
        filtered2.forEach(m ->
                assertTrue(m.getText().toLowerCase().contains(query2.toLowerCase())));

        final String query3 = friend1.getName();
        final List<Message> filtered3 = Filter.messages(messages, query3);
        assertEquals(filtered3.size(), 3);
        filtered3.forEach(m ->
                assertTrue(m.getFriend().getName().toLowerCase().contains(query3.toLowerCase())));
        passed("filter_shouldReturnFilteredMessages");
    }

    @After
    public void after() {
        log(LINE, Color.BLUE);
    }

    private void passed(final String text) {
        log(text + "  ===> ", Mode.WITHOUT_NEW_LINE);
        log("passed", Color.GREEN);
    }
}
