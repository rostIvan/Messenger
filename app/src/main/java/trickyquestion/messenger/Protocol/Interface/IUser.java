package trickyquestion.messenger.Protocol.Interface;

import java.util.UUID;

/**
 * Created by Zen on 09.09.2017.
 */
public interface IUser {
    /**
     * @return User login
     * args - none
     **/
    String getName();

    /**
     * @return UID of user
     * args - none
     */
    UUID getId();

    /**
     * @return bool IsUserAuthenticated
     * args - none
     */
    boolean isAuthenticated();
}
