package trickyquestion.messenger.Protocol;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.List;

import trickyquestion.messenger.Protocol.Interfaces.IClient;
import trickyquestion.messenger.Protocol.Interfaces.IUser;

public class P2PService extends Service {

    private final IBinder mBinder = new LocalBinder();

    private P2PHeartbeat heartbeat;
    private IClient client;

    private P2PNetworkListener network;

    public P2PService() {
    }

    public void onCreate(){
        super.onCreate();
        try {
            MulticastSocket socket = new MulticastSocket(P2PSpecification.MULTICAST_PORT);
            socket.joinGroup(InetAddress.getByName(P2PSpecification.GROUP_IP));
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            socket.setLoopbackMode(false);
            heartbeat = new P2PHeartbeat(socket);
            network = new P2PNetworkListener(socket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStartData(IClient client){
        this.client = client;
    }

    public void Start(){

            heartbeat.ReConfigure(client, this);
            heartbeat.Broadcast();
            network.StartNetwork();

    }

    public void Stop(){
        heartbeat.Stop();
        network.StopNetwork();
    }

    public interface IChangeNetworkListener {
        void NetworkChanged();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        network.ClearListeners();
        return super.onUnbind(intent);
    }

    public class LocalBinder extends Binder {
        public P2PService getService(){
            return P2PService.this;
        }

        public List<IUser> GetNetworkUsers(){
            return P2PService.this.network.GetUsers();
        }

        public void RegisterNetworkChangeListener(IChangeNetworkListener listener){
            P2PService.this.network.RegisterListener(listener);
        }
    }
}
