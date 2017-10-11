package trickyquestion.messenger.p2p_protocol;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.List;

import trickyquestion.messenger.p2p_protocol.interfaces.IClient;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Zen on 11.10.2017.
 */

/**
 * Class which represent P2P protocol and work as service
 */
public class P2PProtocolService extends Service {

    //Binder for this service
    private final LocalBinder mBinder = new LocalBinder();

    //heartbeat object
    private heartbeat heartbeat;

    //network listener object
    private network_listener network;

    //Client data
    private IClient client;

    /**
     * onCreate method create socket, heartbeat and network
     */
    public void onCreate(){
        super.onCreate();
        try {
            //Create socket object
            MulticastSocket socket = new MulticastSocket(PROTOCOL_CFG.MULTICAST_PORT());
            //set socket group
            socket.joinGroup(InetAddress.getByName(PROTOCOL_CFG.MULTICAST_GROUP_IP()));
            //set broadcasting
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            socket.setLoopbackMode(false);
            //create heartbeat object and set socket object
            heartbeat = new heartbeat(socket);
            //create network object and set socket object
            network = new network_listener(socket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interface for registering changing network
     */
    public interface IChangeNetworkListener {
        void NetworkChanged();
    }

    /**
     * Bind to client side
     * @param intent additional data
     * @return binder object which represent service interface
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Close connection to client side
     * @param intent additional data
     * @return is UnBinder successfully
     */
    @Override
    public boolean onUnbind(Intent intent) {
        //clean listeners before unbinding
        network.CleanListeners();
        return super.onUnbind(intent);
    }

    /**
     * Binder class which represent service interface
     * by which client side send command and receiving data
     */
    public class LocalBinder extends Binder {
        /**
         * Return active users
         * @return list of users
         */
        public List<IUser> GetNetworkUsers(){
            return P2PProtocolService.this.network.GetUsers();
        }

        /**
         * Register listeners for changing network
         * @param listener which registered
         */
        public void RegisterNetworkChangeListener(IChangeNetworkListener listener){
            P2PProtocolService.this.network.RegisterListener(listener);
        }

        /**
         * Cleanup all listeners
         */
        public void ResetNetworkChangeListener(){
            P2PProtocolService.this.network.CleanListeners();
        }

        /**
         * Set user data
         * @param data object which sets
         */
        public void SetClientData(IClient data){
            P2PProtocolService.this.client = data;
            //if heartbeat active reconfigure it
            if(heartbeat.getStatus()!= trickyquestion.messenger.p2p_protocol.heartbeat.HeartbeatStatus.Run){
                heartbeat.Stop();
                heartbeat.ReConfigure(data,P2PProtocolService.this);
            }
            //TODO: write network class status and checking it as it work above for heartbeat
        }

        /**
         * Start heartbeat and registering users
         */
        public void Start(){
            heartbeat.ReConfigure(client, P2PProtocolService.this);
            heartbeat.Broadcast();
            network.StartNetwork();

        }

        /**
         * Stop service
         */
        public void Stop(){
            heartbeat.Stop();
            network.StopNetwork();
        }

    }
}
