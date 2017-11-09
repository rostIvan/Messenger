package trickyquestion.messenger.main_screen.main_tabs_content.repository;

import android.support.annotation.NonNull;

import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.util.event_bus_pojo.AddFriendEvent;

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
                public void execute(@NonNull Realm realm) {
                    realm.copyToRealm(friend);
                    onChange();
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
                public void execute(@NonNull Realm realm) {
                    results.deleteFirstFromRealm();
                    onChange();
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
                public void execute(@NonNull Realm realm) {
                    realm.delete(Friend.class);
                    onChange();
                }
            });
        } finally {
            realm.close();
        }
    }

    public static void changeFriendOnlineStatus(final Friend friend, final boolean isOnline) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    friend.setOnline(isOnline);
                }
            });
        } finally {
            realm.close();
        }
    }

    private static void onChange() {
        EventBus.getDefault().post(new AddFriendEvent("Friend add to list"));
    }
}
