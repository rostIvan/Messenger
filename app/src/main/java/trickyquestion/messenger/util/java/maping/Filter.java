package trickyquestion.messenger.util.java.maping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.screen.main.tabs.messages.data.Message;

public class Filter {
    private Filter() {}

    public static List<Friend> friend(final List<Friend> friends, final String searchValue) {
        return find(friends, searchValue);
    }

    public static List<Message> messages(final List<Message> messages, final String searchValue) {
        return find(messages, searchValue);
    }

    public static <T> List<T> find(final List<T> list, final String searchValue) {
        if (searchValue == null) throw new IllegalArgumentException("Search value should be not null");
        if (searchValue.isEmpty()) return list;

        final List<T> result = new ArrayList<>();
        for (T t : list) {
            if (filterPredicate(t, searchValue))
                result.add(t);
        }
        return result;
    }

    private static boolean filterPredicate(Object o, String searchValue) {
        if (o instanceof Friend) {
            final String friendName = (((Friend) o)).getName().toLowerCase(Locale.getDefault());
            final String query = searchValue.toLowerCase(Locale.getDefault());
            return friendName.contains(query);
        }
        else if (o instanceof Message) {
            final String name = (((Message) o)).getFriend().getName().toLowerCase(Locale.getDefault());
            final String message = (((Message) o)).getText().toLowerCase(Locale.getDefault());
            final String query = searchValue.toLowerCase(Locale.getDefault());
            return name.contains(query) || message.contains(query);
        }
        else throw new IllegalArgumentException("Object " + o.getClass() +
                    " are not supported in filter class");
    }
}
