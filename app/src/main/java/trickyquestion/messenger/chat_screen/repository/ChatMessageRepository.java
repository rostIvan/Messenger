package trickyquestion.messenger.chat_screen.repository;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import trickyquestion.messenger.chat_screen.model.ChatMessage;

public class ChatMessageRepository implements IChatMessageRepository {

    @Override
    public void addMessage(final ChatMessage message) {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(message);
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
                .equalTo("text", message.getText())
                .equalTo("time", message.getTime())
                .equalTo("my", message.isMy())
                .findAll();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteAllFromRealm();
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
                }
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public List<ChatMessage> getMessages() {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatMessage.class).findAll();
    }
}
