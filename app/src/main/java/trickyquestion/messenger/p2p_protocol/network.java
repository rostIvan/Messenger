package trickyquestion.messenger.p2p_protocol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import trickyquestion.messenger.p2p_protocol.interfaces.IClient;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Zen on 17.10.2017.
 */

public class network {
    //Requirements
    private Context context;
    private IClient host;

    private ReentrantLock reconfiguring = new ReentrantLock();
    private Condition reconfiguring_end = reconfiguring.newCondition();
    private volatile  boolean reconfiguring_start = false;

    private MulticastSocket CreateBroadcastSocket() throws IOException {
        MulticastSocket socket = new MulticastSocket(PROTOCOL_CFG.MULTICAST_PORT());
        socket.joinGroup(InetAddress.getByName(PROTOCOL_CFG.MULTICAST_GROUP_IP()));
        return socket;
    }

    private class hertbeat_runner implements Runnable{

        private String genHeartbeatPacket(String UserName, UUID UserID, String IP){
            StringBuilder UsrNamePart = new StringBuilder("$$$$$$$$$$$$$$$$$$$");
            UsrNamePart.replace(0,UserName.length(),UserName);
            StringBuilder UserIDPart = new StringBuilder(UserID.toString());
            StringBuilder UserIPPart = new StringBuilder("$$$$$$$$$$$$$$$");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US);//8//18
            String SendTime = sdf.format(Calendar.getInstance().getTime());
            UserIPPart.replace(0,IP.length(),IP);
            return ("P2P_HEARTBEAT:" +
                    UsrNamePart + ":" +
                    UserIDPart + ":" +
                    UserIPPart+ ":" +
                    SendTime +
                    ":P2P_HEARTBEAT");
        }

        /**
         * @return Current wifi ip
         */
        @SuppressLint("DefaultLocale")
        String getCurIP(){
            WifiManager wifiManager = (WifiManager)
                    context.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            return String.format(
                    "%d.%d.%d.%d",
                    (ip & 0xff),
                    (ip >> 8 & 0xff),
                    (ip >> 16 & 0xff),
                    (ip >> 24 & 0xff));
        }

        /**
         * Creating datagram packet which send as heartbeat
         * @return datagram packet which send as heartbeat
         * @throws UnknownHostException
         */
        DatagramPacket CreatePacket() throws UnknownHostException {
            String heartbeat_msg =
                    genHeartbeatPacket
                            (host.getName(),host.getID(),getCurIP());
            return new
                    DatagramPacket( heartbeat_msg.getBytes(),
                    heartbeat_msg.length(),
                    InetAddress.getByName(PROTOCOL_CFG.MULTICAST_GROUP_IP()),
                    PROTOCOL_CFG.MULTICAST_PORT()
            );
        }



        @Override
        public void run() {
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            MulticastSocket socket = null;
            try {
                socket = CreateBroadcastSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    //Checking is reconfiguring started
                    if (reconfiguring_start) {
                        reconfiguring_end.wait();
                        socket = CreateBroadcastSocket();
                    }
                    //Checking is wifi active, if active send packet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                        socket.send(CreatePacket());
                    //Sleep thread
                    Thread.sleep(2500);
                } catch (IOException e) {
                    break;
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private class listener_runner implements Runnable{
        @Override
        public void run() {
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            try {
                MulticastSocket socket = CreateBroadcastSocket();
                String[] received_packet_content;
                for(;;) {
                    //Checking is reconfiguring started
                    if (reconfiguring_start) {
                        reconfiguring_end.wait();
                        socket = CreateBroadcastSocket();
                    }
                    //Checking is wifi active, if active send packet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        //Buffer for received data
                        byte[] received_data = new byte[1000];
                        //received data packet
                        DatagramPacket received_packet = new DatagramPacket(received_data,
                                received_data.length);
                        //receiving packet
                        socket.receive(received_packet);
                        //extracting string from packet
                        String received_packet_str = new String(
                                received_packet.getData(),
                                received_packet.getOffset(),
                                received_packet.getLength()
                        );
                        //Split packet string
                        received_packet_content =
                                received_packet_str.split("[:]");
                        //Checking is valid packet string
                        if (received_packet_content.length == 6) {
                            if (received_packet_content[0].equals("P2P_HEARTBEAT") &&
                                    received_packet_content[5].equals("P2P_HEARTBEAT")) {
                                //Creating parser for time
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(sdf.parse(received_packet_content[4]));
                                //Adding TTL time
                                cal.add(Calendar.SECOND, 10);
                                //adding to list user data
                                //if (received_packet_content[1].substring(0,received_packet_content[1].indexOf("$")).equals())
                                users.add(
                                        new User(
                                                UUID.fromString(received_packet_content[2]),
                                                received_packet_content[1].substring(0, received_packet_content[1].indexOf("$")),
                                                received_packet_content[3].substring(0, received_packet_content[3].indexOf("$")),
                                                cal.getTime()));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class keep_alive_runner implements Runnable{

        @Override
        public void run() {
            //Getting Calendar value
            Calendar time = Calendar.getInstance();
            //Flag signalized when users changed
            boolean network_changed;
            //TODO: correct end thread
            while (true) {
                //Set flag of
                network_changed = false;
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
                    Thread.sleep(PROTOCOL_CFG.HEARTBEAT_FREQUENCY() / 2);
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
        public String get_network_address()  {return IP;}
        public Date getTTL()   {return TTL;}

        public void setTTL(Date NewTTL){TTL = NewTTL;}

        boolean equal(User second){
            if((this.ID.equals(second.ID)) && (this.IP.equals(second.IP)) && (this.UName.equals(second.UName)))
                return true;
            else return false;
        }
    }

    class AsyncList{
        private volatile List<User> users = new ArrayList<>();
        private ReentrantLock list_lock = new ReentrantLock();

        public void add(User new_user){
            list_lock.lock();
            boolean is_new = true;
            for(User user :  users){
                if(user.equal(new_user)) {
                    new_user.setTTL(user.getTTL()); is_new = false; continue;}
            }
            if(is_new) users.add(new_user);
            list_lock.unlock();
        }

        public void remove(User user){
            list_lock.lock();
            users.remove(user);
            list_lock.unlock();
        }

        public List<IUser> get(){
            list_lock.lock();
            List<IUser> ret = new ArrayList<>();
            for(User user : users){
                ret.add(user);
            }
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

    network(IClient host, Context context){
        this.context = context;
        this.host = host;
        Heartbeat = new Thread(new hertbeat_runner());
        Listener = new Thread(new listener_runner());
        KeepAlive = new Thread(new keep_alive_runner());
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
