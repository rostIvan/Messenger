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
import trickyquestion.messenger.buisness.DataProvider;
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
import trickyquestion.messenger.util.java.maping.TypeMapping;

/**
 * Created by Zen on 17.10.2017.
 */

public class P2PProtocolService extends Service{
    private final LocalBinder mBinder = new LocalBinder();
    private IHost host;
    private NetworkPreference servicePreference;

    public P2PProtocolService(){
        //For starting service use start method of LocalBinder
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
    public final boolean onUnbind(Intent intent) {
        //clean listeners before unbinding
        return super.onUnbind(intent);
    }
    
    /**
    * @return bind object to service
    */
    public LocalBinder getBinder() {
        return mBinder;
    }

    private trickyquestion.messenger.p2p_protocol.modules.P2PNetwork p2pNetwork;
    private trickyquestion.messenger.p2p_protocol.modules.P2PAddFriends p2pAddFriends;
    private trickyquestion.messenger.p2p_protocol.modules.P2PMesseges p2pMesseges;

    private boolean started = false;

    /**
    * Bind class for P2PService
    */
    public class LocalBinder extends Binder{

        public boolean isStarted() {return started;}

        /**
        *Starting service modules, if service alredy started modules not restarted
        */
        public void start(){
            if(started) return;
            host = new OHost(getApplicationContext());
            Network.startNetworkListener(getApplicationContext());
            servicePreference = new NetworkPreference(getApplicationContext());
            p2pNetwork = new P2PNetwork(host, getApplicationContext(),servicePreference);
            p2pAddFriends = new P2PAddFriends(getApplicationContext(),servicePreference, p2pNetwork,host);
            p2pMesseges = new P2PMesseges(servicePreference,host);
            Log.d("P2PProtocolService","Service started");
            started = true;
        }

        public void stop(){
            p2pNetwork.stop();
            p2pAddFriends.stop();
            p2pMesseges.stop();
            started = false;
        }

        public List<IUser> getUsers(){
            return p2pNetwork.getUsers();
        }

        public void sendFriendReq(IUser user){
            p2pAddFriends.newAddFriendReq(user);
        }

        public void sendMsg(UUID targetID, String msg){
            List<IFriend> friends = TypeMapping.toIFriendList(
                    FriendRepository.INSTANCE.findAll(), new DataProvider());
            for (IFriend friend : friends) {
                if (friend.getID().equals(targetID)) {
                    p2pMesseges.sendMsg(friend, msg);
                    break;
                }
            }
        }
    }

    /**
    * Inform service about adding friend
    */
    public void onEvent(EAddFriendRequest event) {
        final FriendRequestDialog dialog = new FriendRequestDialog(this, event.getFrom().getName(), event.getFrom().getId().toString());
        dialog.setOnPositiveButtonClickListener((d, i) -> {
            final Friend friend = new Friend(event.getFrom().getName(), event.getFrom().getId(), true);
            FriendRepository.INSTANCE.save(friend);
            Toast.makeText(this, "User: " + event.getFrom().getName() + " add to your friends", Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }
}
