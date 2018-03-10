package trickyquestion.messenger.screen.chat.repository;

import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;
import trickyquestion.messenger.screen.chat.model.ChatMessageM;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeMessageDbEvent;

public class ChatMessageRepository implements IChatMessageRepository {

    @Override
    public void addMessage(final ChatMessageM message) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(realm1 -> {
                realm1.copyToRealm(message);
                onChange();
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public void deleteMessage(final ChatMessageM message) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults results = realm.where(ChatMessageM.class)
                .equalTo("nameFriend", message.getNameFriend())
                .equalTo("idFriend", message.getIdFriend().toString())
                .equalTo("text", message.getText())
                .equalTo("time", message.getTime())
                .equalTo("my", message.isMine())
                .findAll();
        try {
            realm.executeTransaction(realm1 -> {
                results.deleteAllFromRealm();
                onChange();
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public void deleteMessages(String idFriend) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults results = realm.where(ChatMessageM.class)
                .equalTo("idFriend", idFriend)
                .findAll();
        try {
            realm.executeTransaction(realm1 -> {
                results.deleteAllFromRealm();
                onChange();
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public void deleteAllMessages() {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(realm1 -> {
                realm1.delete(ChatMessageM.class);
                onChange();
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public List<ChatMessageM> getAllMessagesFromDB() {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatMessageM.class).findAll();
    }
    @Override
    public List<ChatMessageM> getMessages(final String idFriend) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatMessageM.class).equalTo("idFriend", idFriend).findAll();
    }

    private void onChange() {
        EventBus.getDefault().post(new ChangeMessageDbEvent("Message send"));
    }
}
