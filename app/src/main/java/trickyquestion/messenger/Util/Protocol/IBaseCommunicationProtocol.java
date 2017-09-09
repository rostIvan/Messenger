package trickyquestion.messenger.Util.Protocol;

import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Friend;
import trickyquestion.messenger.MainScreen.MainTabsContent.Model.Message;

public interface IBaseCommunicationProtocol {

    /** @return bool value about previous auth
     *  args - none
     *  on first Start app **/
    boolean isAuthenticated();

    /** @return connection answer
     *  args - none
     *  for check wifi or another connection **/
    boolean isConnected();

    boolean connectToCLient(UserDate userDate);

    interface UserDate {
        String getName();
        UUID getId();
        int getPhoto();
        boolean isOnlime();
        boolean isKnow();
    }

    /** Void method
     * @param userDate
     */
    void setUserDate(final UserDate userDate);

    /** Void method
     *  @param  message it's some Message instance
     *  @param  friend it's some Friend instance
     *  Use for send some message to friend **/
    void sendMessage(final Message message, final Friend friend);

    /** @return Message
     *  args - none
     *  Use for receive message from network **/
    Message receiveMessage();

    /** @return  friends list from network
     *  args - none
     *  Set content to Friend page**/
    List<Friend> getFriendsFromNetwork();
    /** @return  friends list from network
     *  args - none
     *  Set content to Message page**/
    List<Message> getMessagesFromNetwork();

    /** @return online status
     *  args - none
     *  Set content to Message page **/
    boolean isFriendOnline(final Friend friend);
}
