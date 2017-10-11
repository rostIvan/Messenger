package trickyquestion.messenger.Protocol;

/**
 * Created by Zen on 12.09.2017.
 */
public class P2PSpecification {
    /**
     * @return Max size of user name
     */
    static Integer MaxNameSize()
    {
        return 20;
    }

    public static final String GROUP_IP = "239.0.0.1";
    public static final int MULTICAST_PORT = 5000;
}
