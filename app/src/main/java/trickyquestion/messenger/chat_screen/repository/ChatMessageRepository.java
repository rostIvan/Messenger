package trickyquestion.messenger.chat_screen.repository;

import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;
import trickyquestion.messenger.chat_screen.model.ChatMessage;
import trickyquestion.messenger.util.event_bus_pojo.SendMessageEvent;

public class ChatMessageRepository implements IChatMessageRepository {

    @Override
    public void addMessage(final ChatMessage message) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(message);
                    onChange();
                }
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public void deleteMessage(final ChatMessage message) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults results = realm.where(ChatMessage.class)
                .equalTo("table", message.getUserTableName())
                .equalTo("text", message.getText())
                .equalTo("time", message.getTime())
                .equalTo("my", message.isMy())
                .findAll();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteAllFromRealm();
                    onChange();
                }
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public void deleteMessageTable(String table) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults results = realm.where(ChatMessage.class)
                .equalTo("table", table)
                .findAll();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteAllFromRealm();
                    onChange();
                }
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public void deleteAllMessages() {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(ChatMessage.class);
                    onChange();
                }
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public List<ChatMessage> getAllMessagesFromDB() {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatMessage.class).findAll();
    }
    @Override
    public List<ChatMessage> getMessages(final String table) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatMessage.class).equalTo("table", table).findAll();
    }

    private void onChange() {
        EventBus.getDefault().post(new SendMessageEvent("Message send"));
    }
}
