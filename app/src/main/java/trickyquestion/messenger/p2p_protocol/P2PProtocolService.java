package trickyquestion.messenger.p2p_protocol;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import trickyquestion.messenger.main_screen.main_tabs_content.model.Friend;
import trickyquestion.messenger.main_screen.main_tabs_content.repository.FriendsRepository;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.p2p_protocol.events.AuthConfirmed;
import trickyquestion.messenger.p2p_protocol.events.AuthRequest;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.popup_windows.FriendRequestDialog;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.preference.NetworkPreference;

/**
 * Created by Zen on 17.10.2017.
 */

public class P2PProtocolService extends Service{
    private final LocalBinder mBinder = new LocalBinder();
    private IHost host;
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

    public LocalBinder getBinder() {
        return mBinder;
    }

    private P2PNetwork P2PNetwork;
    private P2PAuth P2PAuth;
    private P2PMesseges P2PMesseges;

    private boolean started = false;

    public class LocalBinder extends Binder{
        public void Start(){
            if(started) return;
            host = new Host(getApplicationContext());
            Network.StartNetworkListener(getApplicationContext());
            servicePreference = new NetworkPreference(getApplicationContext());
            P2PNetwork = new P2PNetwork(host, getApplicationContext(),servicePreference);
            P2PAuth = new P2PAuth(getApplicationContext(),servicePreference,P2PNetwork,host);
            P2PMesseges = new P2PMesseges(servicePreference,host,getApplicationContext());
            Log.d("P2PProtocolService","Service started");
            started = true;
        }

        public void Stop(){
            P2PNetwork.Stop();
            P2PAuth.Stop();
            P2PMesseges.Stop();
            started = false;
        }

        public List<IUser> getUsers(){
            return P2PNetwork.getUsers();
        }

        public void SendFriendReq(IUser user){
            if(P2PAuth==null) throw new NullPointerException();
            P2PAuth.NewAuthReq(user);
        }

        public void SendMsg(IFriend target, String msg){
            P2PMesseges.SendMsg(target, msg);
        }
    }

    class Host implements IHost {

        private SharedPreferences preferences;
        private UUID id;


        Host(Context context) {
            id = UUID.fromString("00000000-0000-0000-0000-000000000000");
            preferences = context.getSharedPreferences(Constants.PREFERENCE_AUTH_DATA, Context.MODE_PRIVATE);
        }

        @Override
        public UUID getID() {
            return UUID.fromString(preferences.getString(Constants.EXTRA_KEY_USER_ID, id.toString()));
        }

        @Override
        public String getName() {
            return preferences.getString(Constants.EXTRA_KEY_AUTH_LOGIN, "NULL");
        }

        @Override
        public void setName(String new_name) {
            this.preferences.edit().putString(Constants.EXTRA_KEY_AUTH_LOGIN, new_name).apply();
        }

        @Override
        public void reCreate(UUID id, String name) {
            this.preferences.edit().putString(Constants.EXTRA_KEY_USER_ID, id.toString()).apply();
            this.preferences.edit().putString(Constants.EXTRA_KEY_AUTH_LOGIN,name).apply();
        }
    }


    public void onEvent(AuthRequest event) {
        final FriendRequestDialog dialog = new FriendRequestDialog(this, event.getFrom().getName(), event.getFrom().getID().toString());
        dialog.setOnPositiveButtonClickListener((d, i) -> {
            final Friend friend = new Friend(event.getFrom().getName(), event.getFrom().getID(), true);
            FriendsRepository.addFriend(friend);
            Toast.makeText(this, "User: " + event.getFrom().getName() + " add to your friends", Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }
}
