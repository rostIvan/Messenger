package trickyquestion.messenger.Protocol.Interface;

/**
 * Created by Zen on 09.09.2017.
 */
public interface IAuthListener {
    /**
     * @param from User which ask authenticate
     * @return is this user confirm
     */
    boolean ReceiveAuth(IUser from);
}
