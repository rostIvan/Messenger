package trickyquestion.messenger.p2p_protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.RunnableFuture;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Zen on 11.10.2017.
 */

public class network_listener {
    //Class which represented users in network
    private class Network {
        //Class which represented user in network
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

        }

        //Socket for receiving hertbeats from network
        private volatile MulticastSocket socket;

        //List of active users, used Collections.synchronizedList because list used by two threads
        private volatile List<User> users = Collections.synchronizedList(new ArrayList<User>());

        //adding user to list with checking is this user already in list
        public boolean add(Network.User user) {
            for (Network.User userLink : users)
                //if user exist extend TTL and return true
                if (userLink.getID().equals(user.getID()) &&
                        userLink.getName().equals(user.getName()) &&
                        userLink.get_network_address().equals(user.get_network_address()))
                {userLink.setTTL(user.getTTL()); return true;}
            users.add(user);
            return false;
        }

        //returning user list
        List<IUser> getActiveUsers(){
            List<IUser> ret = new ArrayList<>();
            for(User user : users)
                ret.add(user);
            return ret;
        }

        //create thread for deleting user with elapsed TTL
        private Thread KeepAlive = new Thread(new Runnable() {
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
                    List<Network.User> users_copy = users;
                    for (Network.User user : users_copy) {
                        //if ttl of user elapsed delete user and signalized flag
                        if (System.currentTimeMillis() > user.getTTL().getTime()) {
                            users.remove(user);
                            network_changed = true;
                        }
                    }
                    //if flag signalized inform listener by exec NetworkChanged
                    if (network_changed) NetworkChanged();
                    try {
                        //delay execution thread by HEARTBEAT_FREQUENCY / 2
                        //because user registering is async process
                        Thread.sleep(PROTOCOL_CFG.HEARTBEAT_FREQUENCY() / 2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //Thread for receiving heartbeat packets from other clients in network
        private Thread ReceiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] received_packet_content;
                    for(;;){
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
                                received_packet.getLength()/*,
                                StandardCharsets.UTF_8 */
                        );
                        //Split packet string
                        received_packet_content =
                                received_packet_str.split("[:]");
                        //Checking is valid packet string
                        if(received_packet_content.length==6){
                            if(received_packet_content[0].equals("P2P_HEARTBEAT") &&
                                    received_packet_content[5].equals("P2P_HEARTBEAT")){
                                //Creating parser for time
                                SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss", Locale.US);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(sdf.parse(received_packet_content[4]));
                                //Adding TTL time
                                cal.add(Calendar.SECOND,5);
                                //adding to list user data
                                network.add(
                                        new Network.User(
                                                UUID.fromString(received_packet_content[2]),
                                                received_packet_content[1].substring(0,received_packet_content[1].indexOf("$")),
                                                received_packet_content[3].substring(0,received_packet_content[3].indexOf("$")),
                                                cal.getTime()));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * Constructor for receiving socket object
         * @param socket socket object which used by service
         */
        Network(MulticastSocket socket){
            this.socket = socket;
        }

        /**
         * Start threads
         */
        public void StartNetwork(){
            KeepAlive.start();
            ReceiveThread.start();
        }

        /**
         * Stops threads and cleanup user list
         */
        public void StopNetwork(){
            KeepAlive.interrupt();
            ReceiveThread.interrupt();
            users.clear();
        }

        /**
         * Restart threads
         */
        public void RestartNetwork(){
            if(!KeepAlive.isInterrupted()) KeepAlive.interrupt();
            if(!ReceiveThread.isInterrupted()) ReceiveThread.interrupt();
            users.clear();
            KeepAlive.start();
            ReceiveThread.interrupt();
        }
    }

    //Network object
    private volatile Network network;

    /**
     * Constructor for receiving socket object
     * @param socket socket object which used by service
     */
    network_listener(MulticastSocket socket){
        network = new Network(socket);
    }

    /**
     * Starting receiving users
     */
    public void StartNetwork(){
        network.StartNetwork();
    }

    /**
     * Stops receiving users
     */
    public void StopNetwork(){
        network.StopNetwork();
        network.users.clear();
    }

    /**
     * Restart registering user
     */
    public void RestartNetwork(){
        network.RestartNetwork();
    }

    /**
     * List of listeners
     */
    private List<P2PProtocolService.IChangeNetworkListener> listeners = new ArrayList<>();

    /**
     * Register new listeners
     * @param new_listener
     */
    void RegisterListener(P2PProtocolService.IChangeNetworkListener new_listener){
        listeners.add(new_listener);
    }

    /**
     * Called when add or remove users
     */
    void NetworkChanged(){
        for (P2PProtocolService.IChangeNetworkListener listener: listeners)
            if(listener !=null) listener.NetworkChanged();
    }

    /**
     * @return user list
     */
    List<IUser> GetUsers(){
        return network.getActiveUsers();
    }

    /**
     * Cleaning listeners
     */
    void CleanListeners(){
        listeners.clear();
    }
}
