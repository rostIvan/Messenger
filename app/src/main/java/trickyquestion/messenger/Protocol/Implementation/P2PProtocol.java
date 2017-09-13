package trickyquestion.messenger.Protocol.Implementation;

import trickyquestion.messenger.Protocol.Interface.*;

import java.util.List;

/**
 * Created by Zen on 12.09.2017.
 */
public class P2PProtocol implements IProtocol {
    private List<IAuthListener> auth_listener;
    private List<IMessageListener> message_listener;

    @Override
    public List<IUser> availableUsers() {
        return null;
    }

    @Override
    public List<IAuthenticatedUser> authenticatedUsers() {
        return null;
    }

    @Override
    public boolean AskAuth(IUser User) {
        return false;
    }

    @Override
    public void SendMessage(IAuthenticatedUser Client, IMessage Msg) {

    }

    @Override
    public void SendFile(IAuthenticatedUser Client, String filename) {

    }

    @Override
    public void RegisterAuthReceiver(IAuthListener Listener) {
        auth_listener.add(Listener);
    }

    @Override
    public void RegisterMsgReceivers(IMessageListener Listener) {
        message_listener.add(Listener);
    }
}
