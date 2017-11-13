package trickyquestion.messenger.p2p_protocol;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.network.MSocket;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.NetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.interfaces.IClient;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.util.event_bus_pojo.ChangeUserList;
import trickyquestion.messenger.util.preference.NetworkPreference;

/**
 * Created by Zen on 17.10.2017.
 */

public class P2PNetwork {
    //Requirements
    private Context context;
    private IClient host;
    private NetworkPreference networkPreference;

    private class HeartbeatRunner implements Runnable{
        HeartbeatRunner(){
        }

        private String genHeartbeatPacket(String UserName, UUID UserID, String IP){
            StringBuilder UsrNamePart = new StringBuilder("$$$$$$$$$$$$$$$$$$$");//20
            UsrNamePart.replace(0,UserName.length(),UserName);
            StringBuilder UserIDPart = new StringBuilder(UserID.toString());
            StringBuilder UserIPPart = new StringBuilder("$$$$$$$$$$$$$$$");//15
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US);//19
            String SendTime = sdf.format(Calendar.getInstance().getTime());
            UserIPPart.replace(0,IP.length(),IP);
            return ("P2P_HEARTBEAT:" +
                    UsrNamePart + ":" +
                    UserIDPart + ":" +
                    UserIPPart+ ":" +
                    SendTime +
                    ":P2P_HEARTBEAT");
        }

        @Override
        public void run() {
            for(;;) {
                try {
                    if(Network.GetCurrentNetworkState()==NetworkState.ACTIVE)
                    {
                        String packet_data = genHeartbeatPacket(
                                host.getName(),host.getID(), Network.IPAddress(context));
                        MSocket.SendMsg(
                                packet_data,
                           networkPreference.getMulticastGroupIp(),
                           networkPreference.getMulticastPort());
                    }
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private class ListenerRunner implements Runnable {
        volatile Semaphore networkAvailability = new Semaphore(1);

        ListenerRunner(){
        }

        //@Subscribe(threadMode = ThreadMode.ASYNC)
        public void onMessage(NetworkStateChanged event) {
            if (event.getNewNetworkState() == NetworkState.INACTIVE) {
                try {
                    networkAvailability.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                networkAvailability.release();
            }
        }

        @Override
        public void run() {
            if (Network.GetCurrentNetworkState() != NetworkState.ACTIVE) {
                try {
                    networkAvailability.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String[] received_packet_content;
            for (;;) {
                if (networkAvailability.availablePermits() == 0) {
                    networkAvailability.tryAcquire();
                }
                String received_data = MSocket.Receive(networkPreference.getMulticastGroupIp(),
                        networkPreference.getMulticastPort()
                );
                if (received_data == null) continue;
                //Split packet string
                //Checking is valid packet string
                if (received_data.length() != 120) continue;
                //Parse string
                received_packet_content = received_data.split("[:]");
                try {
                    //Check is valid data
                    if (received_packet_content.length != 6) continue;
                    //Check is valid header
                    if (!(received_packet_content[0].equals("P2P_HEARTBEAT") &&
                            received_packet_content[5].equals("P2P_HEARTBEAT")))
                        continue;
                    //Creating parser for time
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(sdf.parse(received_packet_content[4]));
                    //Adding TTL time
                    cal.add(Calendar.SECOND, 10);
                    users.add(
                            new User(
                                    UUID.fromString(received_packet_content[2]),
                                    received_packet_content[1].substring(0, received_packet_content[1].indexOf("$")),
                                    received_packet_content[3].substring(0, received_packet_content[3].indexOf("$")),
                                    cal.getTime()));
                } catch (ParseException e) {
                    break;
                }
            }
        }
    }

    private class CleanerRunner implements Runnable{

        @Override
        public void run() {
            //TODO: correct end thread
            while (true) {
                //Get copy of user list
                List<User> users_copy = users.getUser();
                for (User user : users_copy) {
                    //if ttl of user elapsed delete user and signalized flag
                    if (System.currentTimeMillis() > user.getTTL().getTime()) {
                        users.remove(user);
                        //network_changed = true;
                    }
                }
                //if flag signalized inform listener by exec NetworkChanged
                //if (network_changed) NetworkChanged();
                try {
                    //delay execution thread by HEARTBEAT_FREQUENCY / 2
                    //because user registering is async process
                    Thread.sleep(networkPreference.getHeartbeatFrequency() / 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Thread Heartbeat;
    private Thread Listener;
    private Thread KeepAlive;

    public class User implements IUser {
        private UUID ID;
        private String UName;
        private String IP;
        //TTL is time to end which user data is valid
        private Date TTL;

        public User(UUID ID,String Name, String IP, Date TTL) {
            this.ID = ID;
            this.UName = Name;
            this.IP = IP;
            this.TTL = TTL;
        }

        public UUID getID()    {return ID;}
        public String getName(){return UName;}
        public String getNetworkAddress()  {return IP;}
        public Date getTTL()   {return TTL;}

        public void setTTL(Date NewTTL){TTL = NewTTL;}

        boolean equal(User second){
            if((this.ID.equals(second.ID)) && (this.IP.equals(second.IP)) && (this.UName.equals(second.UName))) {
                return true;
            }
            else return false;
        }
    }

    class AsyncList{
        private volatile List<User> users = new ArrayList<>();
        private ReentrantLock list_lock = new ReentrantLock();

        public void add(User new_user){
            list_lock.lock();
            boolean is_new = true;
            for(User user :  users) {
                if (user.equal(new_user)) {
                        user.setTTL(new_user.getTTL());
                    is_new = false;
                    break;
                }
            }
            if(is_new) {
                users.add(new_user);
                EventBus.getDefault().post(new ChangeUserList(new_user,true));
            }
            list_lock.unlock();
        }

        public void remove(User user){
            list_lock.lock();
            users.remove(user);
            EventBus.getDefault().post(new ChangeUserList(user,false));
            list_lock.unlock();
        }

        public List<IUser> get(){
            list_lock.lock();
            List<IUser> ret = new ArrayList<>();
            ret.addAll(users);
            list_lock.unlock();
            return ret;
        }

        private List<User> getUser(){
            list_lock.lock();
            List<User> ret = new ArrayList<>();
            for(User user : users)
                ret.add(user);
            list_lock.unlock();
            return ret;
        }
    }

    private AsyncList users = new AsyncList();

    P2PNetwork(IClient host, Context context, NetworkPreference networkPreference){
        this.context = context;
        this.host = host;
        this.networkPreference = networkPreference;
        Heartbeat = new Thread(new HeartbeatRunner());
        Listener = new Thread(new ListenerRunner());
        KeepAlive = new Thread(new CleanerRunner());
    }

    public void Start(){
        Heartbeat.start();
        Listener.start();
        KeepAlive.start();
    }

    public void Stop(){
        Heartbeat.interrupt();
        Listener.interrupt();
    }

    public List<IUser> getUsers(){
        return users.get();
    }
}
