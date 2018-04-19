package trickyquestion.messenger.p2p_protocol;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.data.repository.FriendRepository;
import trickyquestion.messenger.p2p_protocol.events.EAddFriendRequest;
import trickyquestion.messenger.p2p_protocol.objects.OHost;
import trickyquestion.messenger.screen.main.tabs.friends.data.Friend;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.p2p_protocol.modules.P2PAddFriends;
import trickyquestion.messenger.p2p_protocol.modules.P2PMesseges;
import trickyquestion.messenger.p2p_protocol.modules.P2PNetwork;
import trickyquestion.messenger.screen.popup_windows.FriendRequestDialog;
import trickyquestion.messenger.util.android.preference.NetworkPreference;
import trickyquestion.messenger.util.java.maping.TypeCasting;

/**
 * Created by Zen on 17.10.2017.
 */

public class P2PProtocolService extends Service{
    private final LocalBinder mBinder = new LocalBinder();
    private IHost host;
    private NetworkPreference servicePreference;

    public P2PProtocolService(){
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
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
    
    /**
    * @return bind object to service
    */
    public LocalBinder getBinder() {
        return mBinder;
    }

    private trickyquestion.messenger.p2p_protocol.modules.P2PNetwork P2PNetwork;
    private trickyquestion.messenger.p2p_protocol.modules.P2PAddFriends P2PAddFriends;
    private trickyquestion.messenger.p2p_protocol.modules.P2PMesseges P2PMesseges;

    private boolean started = false;

    /**
    * Bind class for P2PService
    */
    public class LocalBinder extends Binder{

        public boolean IsStarted() {return started;}

        /**
        *Starting service modules, if service alredy started modules not restarted
        */
        public void Start(){
            if(started) return;
            host = new OHost(getApplicationContext());
            Network.StartNetworkListener(getApplicationContext());
            servicePreference = new NetworkPreference(getApplicationContext());
            P2PNetwork = new P2PNetwork(host, getApplicationContext(),servicePreference);
            P2PAddFriends = new P2PAddFriends(getApplicationContext(),servicePreference,P2PNetwork,host);
            P2PMesseges = new P2PMesseges(servicePreference,host,getApplicationContext());
            Log.d("P2PProtocolService","Service started");
            started = true;
        }

        public void Stop(){
            P2PNetwork.Stop();
            P2PAddFriends.Stop();
            P2PMesseges.Stop();
            started = false;
        }

        public List<IUser> getUsers(){
            return P2PNetwork.getUsers();
        }

        public void SendFriendReq(IUser user){
            P2PAddFriends.NewAddFriendReq(user);
        }

        public void SendMsg(UUID targetID, String msg){
            List<IFriend> friends = TypeCasting.castToIFriendList(FriendRepository.INSTANCE.findAll());
            for (IFriend friend : friends) {
                if (friend.getID().equals(targetID)) {
                    P2PMesseges.SendMsg(friend, msg);
                    break;
                }
            }
        }
    }

    /**
    * Inform service about adding friend
    */
    public void onEvent(EAddFriendRequest event) {
        final FriendRequestDialog dialog = new FriendRequestDialog(this, event.getFrom().getName(), event.getFrom().getID().toString());
        dialog.setOnPositiveButtonClickListener((d, i) -> {
            final Friend friend = new Friend(event.getFrom().getName(), event.getFrom().getID(), true);
            FriendRepository.INSTANCE.save(friend);
            Toast.makeText(this, "User: " + event.getFrom().getName() + " add to your friends", Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }
}
