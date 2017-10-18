package trickyquestion.messenger.p2p_protocol;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.interfaces.IClient;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.util.Constants;

/**
 * Created by Zen on 17.10.2017.
 */

public class P2ProtocolService extends Service{
    private final LocalBinder mBinder = new LocalBinder();
    private IClient host;

    public P2ProtocolService(){
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

    private network Network;

    public class LocalBinder extends Binder{
        public void Start(){
            host = new Host(getApplicationContext());
            Network = new network(host, getApplicationContext());
            Network.Start();
        }

        public void Stop(){
            Network.Stop();
        }

        public List<IUser> getUsers(){
            return Network.getUsers();
        }
    }

    class Host implements IClient{

        private SharedPreferences preferences;
        private UUID id;


        Host(Context context){
            id = UUID.fromString("00000000-0000-0000-0000-000000000000");
            preferences = context.getSharedPreferences(Constants.EXTRA_KEY_AUTH_DATA, Context.MODE_PRIVATE);
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
        public void recreate(UUID id, String name, String network_address) {
        }

        @Override
        public String get_network_address() {
            return "127.0.0.1";
        }
    }
}
