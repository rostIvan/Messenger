package trickyquestion.messenger.Protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import trickyquestion.messenger.Protocol.Interfaces.IUser;


/**
 * Created by Zen on 26.09.2017.
 */

public class P2PNetworkListener {
    private class Network {

	    public class User implements IUser {
		    private UUID ID;
		    private String UName;
		    private String IP;
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

        private volatile MulticastSocket socket;

        private volatile List<Network.User> users = Collections.synchronizedList(new ArrayList<User>());

        public boolean add(Network.User user) {
            for (Network.User userLink : users)
                if (userLink.getID().equals(user.getID()) &&
                        userLink.getName().equals(user.getName()) &&
                        userLink.get_network_address().equals(user.get_network_address()))
                {userLink.setTTL(user.getTTL()); return true;}
            users.add(user);
            return false;
        }

        List<IUser> getActiveUsers(){
            List<IUser> ret = new ArrayList<>();
            for(User user : users)
                ret.add(user);
            return ret;
        }

        private class UserKeepAlive implements Runnable {
            public void run() {
                Calendar time = Calendar.getInstance();
                while (true) {
                    List<Network.User> users_copy = users;
                    for (Network.User user : users_copy) {
                        if (System.currentTimeMillis() > user.getTTL().getTime()) {
                            users.remove(user);
                        }
                    }
                    try {
                        Thread.sleep(1250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private Thread KeepAlive = new Thread(new UserKeepAlive());

        private Thread ReceiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] received_packet_content;
                    for(;;){
                        byte[] received_data = new byte[1000];
                        DatagramPacket received_packet = new DatagramPacket(received_data,
                                received_data.length);
                        socket.receive(received_packet);
                        String received_packet_str = new String(
                                received_packet.getData(),
                                received_packet.getOffset(),
                                received_packet.getLength()/*,
                                StandardCharsets.UTF_8 */
                        );
                        received_packet_content =
                                received_packet_str.split("[:]");
                        if(received_packet_content.length==6){
                            if(received_packet_content[0].equals("P2P_HEARTBEAT") &&
                                    received_packet_content[5].equals("P2P_HEARTBEAT")){
                                SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss", Locale.US);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(sdf.parse(received_packet_content[4]));
                                cal.add(Calendar.SECOND,5);
                                network.add(
                                        new Network.User(
                                        UUID.fromString(received_packet_content[2]),
                                        received_packet_content[1].substring(0,received_packet_content[1].indexOf("$")),
                                        received_packet_content[3].substring(0,received_packet_content[3].indexOf("$")),
                                        cal.getTime()));
                                NetworkChanged();
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

        Network(MulticastSocket socket){
            this.socket = socket;
        }

        public void StartNetwork(){
            KeepAlive.start();
            ReceiveThread.start();
        }

        public void StopNetwork(){
            KeepAlive.interrupt();
            ReceiveThread.interrupt();
        }
        public void RestartNetwork(){
            if(!KeepAlive.isInterrupted()) KeepAlive.interrupt();
            if(!ReceiveThread.isInterrupted()) ReceiveThread.interrupt();
            KeepAlive.start();
            ReceiveThread.interrupt();
        }
    }
    private volatile Network network;


    P2PNetworkListener(MulticastSocket socket){
        network = new Network(socket);
    }

    public void StartNetwork(){
        network.StartNetwork();
    }

    public void StopNetwork(){
        network.StopNetwork();
    }

    public void RestartNetwork(){
        network.RestartNetwork();
    }


    private List<P2PService.IChangeNetworkListener> listeners = new ArrayList<>();

    void RegisterListener(P2PService.IChangeNetworkListener new_listener){
        listeners.add(new_listener);
    }

    void ClearListeners(){
        listeners.clear();
    }

    void NetworkChanged(){
        for (P2PService.IChangeNetworkListener listener: listeners)
            if(listener !=null) listener.NetworkChanged();
    }

    List<IUser> GetUsers(){
        return network.getActiveUsers();
    }
}
