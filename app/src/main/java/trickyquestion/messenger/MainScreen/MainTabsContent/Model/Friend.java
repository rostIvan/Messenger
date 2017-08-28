package trickyquestion.messenger.MainScreen.MainTabsContent.Model;

import java.util.ArrayList;
import java.util.List;

public class Friend {

    private String name;

    public Friend(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public static List<Friend> getFriends(int size) {
        final List<Friend> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Friend friend = new Friend("Some name");
            list.add(friend);
        }
        return list;
    }
}
