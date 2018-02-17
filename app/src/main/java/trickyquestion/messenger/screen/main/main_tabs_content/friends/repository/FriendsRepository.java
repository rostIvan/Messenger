package trickyquestion.messenger.screen.main.main_tabs_content.friends.repository;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import trickyquestion.messenger.screen.main.main_tabs_content.friends.model.Friend;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeFriendDataBaseEvent;

public class FriendsRepository {

    public static List<Friend> getFriends() {
        final Realm realm = Realm.getDefaultInstance();
        List<Friend> res = new ArrayList<>();
        res.addAll(realm.where(Friend.class).findAll().sort("name", Sort.ASCENDING));
        return res;
    }

    public static void addFriend(final Friend friend) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(r -> {
                r.copyToRealm(friend);
                onChange();
            });
        } finally {
            realm.close();
        }
    }
    public static void updateFriend(final String id, final IFriend friend) {
        final Realm realm = Realm.getDefaultInstance();
        final Friend friendUpdate = realm.where(Friend.class).
                equalTo("id", id).
                findFirst();
//        if (friendUpdate == null) return;
        try {
            realm.executeTransaction(r -> {
                friendUpdate.setName(friend.getName());
                friendUpdate.setOnline(true);
                friendUpdate.setId(friend.getID());
                onChange();
            });
        } finally {
            realm.close();
        }
    }

    public static void updateFriend(final String id, final IUser user) {
        final Realm realm = Realm.getDefaultInstance();
        final Friend friendUpdate = realm.where(Friend.class).
                equalTo("id", id).
                findFirst();
//        if (friendUpdate == null) return;
        try {
            realm.executeTransaction(r -> {
                friendUpdate.setName(user.getName());
                friendUpdate.setOnline(true);
                friendUpdate.setId(user.getID());
                onChange();
            });
        } finally {
            realm.close();
        }
    }

    public static void deleteFriend(final Friend friend) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults results = realm.where(Friend.class).equalTo("id", friend.getId().toString()).findAll();
        try {
            realm.executeTransaction(r -> {
                results.deleteFirstFromRealm();
                onChange();
            });
        } finally {
            realm.close();
        }
    }

    public static Friend getFriend(final String id) {
        final Realm realm = Realm.getDefaultInstance();
        final Friend friend = realm.where(Friend.class).equalTo("id", id).findFirst();
        realm.close();
        return friend;
    }

    public static void deleteAllFriends() {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(r -> {
                r.delete(Friend.class);
                onChange();
            });
        } finally {
            realm.close();
        }
    }

    public static void changeFriendOnlineStatus(final Friend friend, final boolean isOnline) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(r -> friend.setOnline(isOnline));
        } finally {
            realm.close();
        }
    }

    private static void onChange() {
        EventBus.getDefault().post(new ChangeFriendDataBaseEvent("Change data in db"));
    }

    public static void updateFriendsStatus(List<Friend> friendList, List<IUser> users) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(r -> {
                for (Friend friend : friendList) {
                    friend.setOnline(false);
                    for (IUser user : users) {
                        if (friend.getId().equals(user.getID().toString()))
                            friend.setOnline(true);
                    }
                }
            });
        } finally {
            realm.close();
        }
    }
}
