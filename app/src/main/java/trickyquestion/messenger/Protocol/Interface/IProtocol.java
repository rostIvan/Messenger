package trickyquestion.messenger.Protocol.Interface;

import java.util.List;

/**
 * Created by Zen on 09.09.2017.
 */
public interface IProtocol {
    /**
     * @return List of active user in local network
     */
    List<IUser> availableUsers();

    /**
     * @return Known users
     */
    List<IAuthenticatedUser> authenticatedUsers();

    /**
     * Authenticate with user
     * If user confirm then this client add to authenticatedUsers list
     * If user already authenticate return true
     *
     * @param User Client with whom this client try authenticate
     * @return Is client confirm auth
     */
    boolean AskAuth(IUser User);

    /**
     * Send message to specific user
     *
     * @param Client Client to whom message must be send
     * @param Msg    Message which must be send
     */
    void SendMessage(IAuthenticatedUser Client, IMessage Msg);

    /**
     * Send file to specific user
     *
     * @param Client   Client to whom file must be sent
     * @param filename Name of file which must be sent
     */
    void SendFile(IAuthenticatedUser Client, String filename);

    /**
     * Register listeners to receiving messages
     *
     * @param Listener listener which registered
     */
    void RegisterMsgReceivers(IMessageListener Listener);

    /**
     * Register auth rec
     *
     * @param Listener
     */
    void RegisterAuthReceiver(IAuthListener Listener);
}
