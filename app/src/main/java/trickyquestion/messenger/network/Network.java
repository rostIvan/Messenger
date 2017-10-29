package trickyquestion.messenger.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.sip.SipAudioCall;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;
import static android.net.NetworkInfo.State.CONNECTED;

public class Network {

    private static List<NetworkListener> NetworkListeners = new ArrayList<>();

    private static class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (networkInfo.getState() == CONNECTED) {
                    if(Network.networkState!=NetworkState.ACTIVE)
                        for (NetworkListener listener: NetworkListeners) {
                            if(listener != null)
                                listener.OnNetworkStateChange(NetworkState.ACTIVE);
                        }
                    Network.networkState = NetworkState.ACTIVE;
                }
                if(Network.networkState!=NetworkState.INACTIVE)
                    for (NetworkListener listener: NetworkListeners) {
                        if(listener != null)
                            listener.OnNetworkStateChange(NetworkState.ACTIVE);
                    }
            }
//            if ("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(action)) {
//                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
//                if (state == 13) {
//                    WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
//                    SendToast(context, "Wifi hot-spot connected, ip: " + intToInetAddress(wifiManager.getDhcpInfo().ipAddress).getHostAddress());
//                    getLocalIpAddress();
//                } else{
//                    SendToast(context, "Wifi hot-spot not connected");
//                }
//                return;
//            }
        }
    }

    static private boolean isStarted = false;

    static public boolean StartNetworkListener(Context context){
        if(isStarted) return false;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        context.registerReceiver(new Receiver(),filter);
        return true;
    }

    private static String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }
        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();
        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            ipAddressString = null;
        }
        return ipAddressString;
    }

    @Nullable
    public static String broadcastAdress(Context context){
        if(networkState == NetworkState.ACTIVE){
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            DhcpInfo dhcp = wifiManager.getDhcpInfo();
            if(dhcp != null){
                int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
                byte[] quads = new byte[4];
                for(int i =0;i<4;i++){
                    quads[i]=(byte)((broadcast >> i * 8)&0xFF);
                }
                try {
                    return InetAddress.getByAddress(quads).toString();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Nullable
    public static String IPAddress(Context context){
        if(networkState == NetworkState.ACTIVE) return wifiIpAddress(context);
        else return null;
    }

    private static NetworkState networkState = NetworkState.INACTIVE;

    @Contract(pure = true)
    public static NetworkState GetCurrentNetworkState(){return networkState;}

    public static void RegisterListener(NetworkListener listener){
        NetworkListeners.add(listener);
    }

    public interface NetworkListener {
        void OnNetworkStateChange(NetworkState newState);
    }
}
