package trickyquestion.messenger.p2p_protocol;

/**
 * Created by Zen on 11.10.2017.
 */

public class P2PProtocolCfg {
    //Delay for sending heartbeat packet
    static volatile int heartbeatFrequency = 2500;
    //IP group for broadcasting, similar to in messenger groups
    static volatile String multicastGroupIP  = "111.111.111.111";
    //Port for broadcasting
    static volatile int multicastPort = 5000;

    static int CurrentHeartbeatFrequency(){
        return heartbeatFrequency;
    }

    static String CurrentMulticastGroupIP(){
        return multicastGroupIP;
    }
    static int CurrentMulticastPort(){
        return multicastPort;
    }

    void SetHeartbeatFrequency(int heartbeat_frequency){
        heartbeatFrequency = heartbeat_frequency;
    }

    void SetMulticastGroup_ip(String multicast_group_ip){
        multicastGroupIP = multicast_group_ip;
    }

    void SetMulticastPort(int multicast_port){
        multicastPort = multicast_port;
    }
}
