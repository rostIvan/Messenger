package trickyquestion.messenger.network.events;

import trickyquestion.messenger.network.NetworkState;

public class ENetworkStateChanged {
    private NetworkState state;
    ENetworkStateChanged(NetworkState newState){
        state = newState;
    }
    public NetworkState getNewNetworkState(){return state;}
}
