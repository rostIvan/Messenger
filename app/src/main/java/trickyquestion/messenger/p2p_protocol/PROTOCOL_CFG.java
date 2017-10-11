package trickyquestion.messenger.p2p_protocol;

/**
 * Created by Zen on 11.10.2017.
 */

public class PROTOCOL_CFG {
    //Delay for sending heartbeat packet
    static volatile int heartbeat_frequency = 2500;
    //IP group for broadcasting, similar to in messenger groups
    static volatile String multicast_group_ip  = "239.0.0.1";
    //Port for broadcasting
    static volatile int multicast_port = 5000;

    static int HEARTBEAT_FREQUENCY(){
        return heartbeat_frequency;
    }

    static String MULTICAST_GROUP_IP(){
        return multicast_group_ip;
    }
    static int MULTICAST_PORT(){
        return multicast_port;
    }

    void set_heartbeat_frequency(int heartbeat_frequency){
        this.heartbeat_frequency = heartbeat_frequency;
    }

    void set_multicast_group_ip(String multicast_group_ip){
        this.multicast_group_ip = multicast_group_ip;
    }

    void set_multicast_port(int multicast_port){
        this.multicast_port = multicast_port;
    }
}
