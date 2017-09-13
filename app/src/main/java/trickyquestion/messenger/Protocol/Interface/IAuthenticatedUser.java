package trickyquestion.messenger.Protocol.Interface;

/**
 * Created by Zen on 09.09.2017.
 */
public interface IAuthenticatedUser extends IUser {
    /**
     * @return is this user available in local network
     */
    boolean isOnline();
}
