package trickyquestion.messenger.main_screen.main_tabs_content.repository;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;

public class FriendsRepository {

    public static List<Friend> getFriends() {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Friend.class).findAll().sort("name", Sort.ASCENDING);
    }

    public static void addFriend(final Friend friend) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(friend);
                }
            });
        } finally {
            realm.close();
        }
    }

    public static void deleteFriend(final Friend friend) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults results = realm.where(Friend.class).equalTo("name", friend.getName()).findAll();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteFirstFromRealm();
                }
            });
        } finally {
            realm.close();
        }
    }

    public static void deleteAllFriends() {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(Friend.class);
                }
            });
        } finally {
            realm.close();
        }
    }
}
