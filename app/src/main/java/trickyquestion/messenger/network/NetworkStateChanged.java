package trickyquestion.messenger.network;

public class NetworkStateChanged{
    private NetworkState state;
    NetworkStateChanged(NetworkState newState){
        state = newState;
    }
    public NetworkState getNewNetworkState(){return state;}
}
