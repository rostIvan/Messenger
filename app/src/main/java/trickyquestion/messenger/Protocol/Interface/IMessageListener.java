package trickyquestion.messenger.Protocol.Interface;
/**
 * Created by Zen on 09.09.2017.
 */

/**
 * Interface for receiving message
 */
public interface IMessageListener {
    /**
     * callback for receiving messages
     *
     * @param from Authenticated user which send message
     * @param msg  Message sent by user
     */
    void receiveMsg(IAuthenticatedUser from, IMessage msg);
}
