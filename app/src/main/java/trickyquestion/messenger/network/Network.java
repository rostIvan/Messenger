package trickyquestion.messenger.network;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.sip.SipAudioCall;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import de.greenrobot.event.EventBus;

import static android.content.Context.WIFI_SERVICE;
import static android.net.NetworkInfo.State.CONNECTED;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLED;

public class Network {

    private static class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (networkInfo.getState() == CONNECTED)
                    if(Network.networkState!=NetworkState.ACTIVE) {
                        EventBus.getDefault().post(new NetworkStateChanged(NetworkState.ACTIVE));
                        Network.networkState = NetworkState.ACTIVE;
                    }
                else
                    if(Network.networkState!=NetworkState.INACTIVE) {
                        EventBus.getDefault().post(new NetworkStateChanged(NetworkState.INACTIVE));
                        Network.networkState = NetworkState.INACTIVE;
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
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        if(wifiManager.getWifiState()==WIFI_STATE_ENABLED)
            networkState=NetworkState.ACTIVE;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        context.registerReceiver(new Receiver(),filter);
        return true;
    }

    @SuppressLint("DefaultLocale")
    private static String wifiIpAddress(Context context) {
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

    @Nullable
    public static String broadcastAdress(Context context) throws SocketException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        for (Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces(); niEnum.hasMoreElements();) {
            NetworkInterface ni = niEnum.nextElement();
            if (!ni.isLoopback()) {
                for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses()) {
                    return interfaceAddress.getBroadcast().toString().substring(1);
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
}

