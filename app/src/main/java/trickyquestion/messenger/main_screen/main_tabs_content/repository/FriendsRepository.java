package trickyquestion.messenger.main_screen.main_tabs_content.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;

public class FriendsRepository {
    private static List<Friend> friends;

    public static List<Friend> getFriends() {
        final Realm realm = Realm.getDefaultInstance();
        friends = realm.where(Friend.class).findAll();
        return friends;
    }

    public static void addFriend(final Friend friend) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(friend);
            }
        });
    }

    public static void deleteFriend(final Friend friend) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults results = realm.where(Friend.class).equalTo("name", friend.getName()).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public static void deleteAllFriends() {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Friend.class);
            }
        });
    }

    public static List<Friend> createRandom(int size) {
        return Friend.getFriends(size);
    }
}
