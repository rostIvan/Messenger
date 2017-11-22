package trickyquestion.messenger.p2p_protocol;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.p2p_protocol.interfaces.IClient;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.preference.NetworkPreference;

/**
 * Created by Zen on 17.10.2017.
 */

public class P2PProtocolService extends Service{
    private final LocalBinder mBinder = new LocalBinder();
    private IClient host;
    private NetworkPreference servicePreference;

    public P2PProtocolService(){
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
        return super.onUnbind(intent);
    }

    private P2PNetwork P2PNetwork;

    private boolean started = false;

    public class LocalBinder extends Binder{
        public void Start(){
            if(started) return;
            host = new Host(getApplicationContext());
            Network.StartNetworkListener(getApplicationContext());
            servicePreference = new NetworkPreference(getApplicationContext());
            P2PNetwork = new P2PNetwork(host, getApplicationContext(),servicePreference);
            P2PNetwork.Start();
            started = true;
        }

        public void Stop(){
            P2PNetwork.Stop();
        }

        public List<IUser> getUsers(){
            return P2PNetwork.getUsers();
        }
    }

    class Host implements IClient{

        private SharedPreferences preferences;
        private UUID id;


        Host(Context context){
            id = UUID.fromString("00000000-0000-0000-0000-000000000000");
            preferences = context.getSharedPreferences(Constants.PREFERENCE_AUTH_DATA, Context.MODE_PRIVATE);
        }

        @Override
        public UUID getID() {
            return UUID.fromString(preferences.getString(Constants.EXTRA_KEY_USER_ID, id.toString()));
        }

        @Override
        public String getName() {
            return preferences.getString(Constants.EXTRA_KEY_AUTH_LOGIN,"NULL");
        }

        @Override
        public String getImage() {
            return "";
        }

        @Override
        public void setName(String new_name) {
        }

        @Override
        public void reCreate(UUID id, String name, String network_address) {
        }

        @Override
        public String getNetworkAddress() {
            return "127.0.0.1";
        }
    }
}
