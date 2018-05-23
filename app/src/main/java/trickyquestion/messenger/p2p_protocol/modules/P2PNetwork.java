package trickyquestion.messenger.p2p_protocol.modules;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.network.multicast_socket.MSocket;
import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.network.NetworkState;
import trickyquestion.messenger.network.events.ENetworkStateChanged;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;
import trickyquestion.messenger.p2p_protocol.objects.OUser;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.android.event_bus_pojo.ChangeUserList;
import trickyquestion.messenger.util.android.preference.NetworkPreference;
import trickyquestion.messenger.util.java.string_helper.FixedString;

/**
 * Created by Zen on 17.10.2017.
 */

public class P2PNetwork {
    //Requirements
    private Context context;
    private IHost host;
    private NetworkPreference networkPreference;

    private static final String LOG_TAG = "P2PNetwork";

    private class HeartbeatRunner implements Runnable{
        HeartbeatRunner(){
        }

        private String genHeartbeatPacket(@NotNull String userName, @NotNull UUID userID, @NotNull String ip){
            String usrNamePart = FixedString.fill(userName,'$',20);
            String userIPPart = FixedString.fill(ip,'$',15);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US);//19
            String sendTime = sdf.format(Calendar.getInstance().getTime());
            return ("P2P_HEARTBEAT:" +
                    usrNamePart + ":" +
                    userID.toString() + ":" +
                    userIPPart+ ":" +
                    sendTime +
                    ":P2P_HEARTBEAT");
        }

        @Override
        public void run() {
            while(!Thread.interrupted()) {
                try {
                    if(Network.getCurrentNetworkState()==NetworkState.ACTIVE)
                    {
                        String ipAddress = Network.ipAddress(context);
                        if(ipAddress!=null) {
                            String packetData = genHeartbeatPacket(
                                    host.getName(), host.getID(), ipAddress);
                            MSocket.sendMsg(
                                    Network.ipAddress(context),
                                    packetData,
                                    networkPreference.getMulticastGroupIp(),
                                    networkPreference.getMulticastPort()
                            );
                        }
                    }
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private class ListenerRunner implements Runnable {
        volatile Semaphore networkAvailability = new Semaphore(1);

        ListenerRunner(){
            networkAvailability.release();
            EventBus.getDefault().register(this);
        }

        public void onEvent(ENetworkStateChanged event) throws InterruptedException {
            if (event.getNewNetworkState() == NetworkState.INACTIVE) {
                try {
                    networkAvailability.acquire();
                } catch (InterruptedException e) {
                    Log.d(LOG_TAG, e.getMessage());
                    throw e;
                }
            } else {
                networkAvailability.release();
            }
        }

        boolean validPacketContent(String[] packet){
            //Check is valid data
            if (packet.length != 6) return false;
            //Check is valid header
            return packet[0].equals("P2P_HEARTBEAT") &&
                    packet[5].equals("P2P_HEARTBEAT");
        }

        private void addUserDataToList(String[] packetData) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US);
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(packetData[4]));
                //Adding TTL time
                cal.add(Calendar.MILLISECOND, Constants.DEFAULT_USER_TTL);
                users.add(
                        new OUser(
                                UUID.fromString(packetData[2]),
                                packetData[1].substring(0, packetData[1].indexOf('$')),
                                packetData[3].substring(0, packetData[3].indexOf('$')),
                                cal.getTime()));
            } catch (ParseException e) {
                Log.d(LOG_TAG, e.getMessage());
            }
        }

        @Override
        public void run() {
            String[] receivedPacketContent;
            while(!Thread.interrupted()){
                if (networkAvailability.availablePermits() == 0) {
                    if(networkAvailability.tryAcquire()) continue;
                    networkAvailability.release();
                }
                String receivedData = MSocket.receive(
                        Network.ipAddress(context),
                        networkPreference.getMulticastGroupIp(),
                        networkPreference.getMulticastPort()
                );
                if (receivedData != null && receivedData.length() == 121) {
                    receivedPacketContent = receivedData.split("[:]");
                    if (validPacketContent(receivedPacketContent))
                        addUserDataToList(receivedPacketContent);
                }
            }
        }
    }

    private class CleanerRunner implements Runnable{

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                //Get copy of user list
                List<OUser> usersCopy = users.getUser();
                for (OUser user : usersCopy) {
                    //if ttl of user elapsed delete user and signalized flag
                    if (System.currentTimeMillis() > user.getTTL().getTime()) {
                        users.remove(user);
                    }
                }
                try {
                    //delay execution thread by HEARTBEAT_FREQUENCY / 2
                    //because user registering is async process
                    Thread.sleep(Constants.DEFAULT_HEARTBEAT_PACKET_FREQUENCY);
                } catch (InterruptedException e) {
                    Log.d(LOG_TAG, e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private Thread heartbeatThread;
    private Thread listenerThread;
    private Thread keepAliveThread;

    public static class AsyncList{
        private volatile List<OUser> users = new ArrayList<>();
        private ReentrantLock listLock = new ReentrantLock();

        public void add(OUser newUser){
            listLock.lock();
            boolean isNew = true;
            for(OUser user :  users) {
                if (user.equals(newUser)) {
                    user.setTTL(newUser.getTTL());
                    if(!user.equalUserName(newUser)){
                        user.setName(newUser.getName());
                    }
                    isNew = false;
                    break;
                }
            }
            if(isNew) {
                users.add(newUser);
                EventBus.getDefault().post(new ChangeUserList(newUser,true));
            }
            listLock.unlock();
        }

        void remove(OUser user){
            listLock.lock();
            users.remove(user);
            EventBus.getDefault().post(new ChangeUserList(user,false));
            listLock.unlock();
        }

        public List<IUser> get(){
            listLock.lock();
            List<IUser> ret = new ArrayList<>();
            ret.addAll(users);
            listLock.unlock();
            return ret;
        }

        private List<OUser> getUser(){
            listLock.lock();
            List<OUser> ret = new ArrayList<>();
            ret.addAll(users);
            listLock.unlock();
            return ret;
        }
    }

    private AsyncList users = new AsyncList();

    public P2PNetwork(IHost host, Context context, NetworkPreference networkPreference){
        this.context = context;
        this.host = host;
        this.networkPreference = networkPreference;
        heartbeatThread = new Thread(new HeartbeatRunner());
        listenerThread = new Thread(new ListenerRunner());
        keepAliveThread = new Thread(new CleanerRunner());
        heartbeatThread.start();
        listenerThread.start();
        keepAliveThread.start();
    }

    public void stop(){
        heartbeatThread.interrupt();
        keepAliveThread.interrupt();
        listenerThread.interrupt();
    }

    public List<IUser> getUsers(){
        return users.get();
    }
}