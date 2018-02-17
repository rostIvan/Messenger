package trickyquestion.messenger.network;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;

//import org.jetbrains.annotations.Contract;

import de.greenrobot.event.EventBus;
import trickyquestion.messenger.network.events.ENetworkStateChanged;

import static android.content.Context.WIFI_SERVICE;

public class Network {
    private static volatile NetworkState networkState = NetworkState.INACTIVE;

    private static class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (networkInfo.isConnected())
                    if(Network.networkState != NetworkState.ACTIVE) {
                        Network.networkState = NetworkState.ACTIVE;
                    } else return;
                else
                    if(Network.networkState != NetworkState.INACTIVE) {
                        Network.networkState = NetworkState.INACTIVE;
                    } else return;
                    EventBus.getDefault().post(new ENetworkStateChanged(Network.networkState));
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
    static private Receiver receiver = new Receiver();

    static public boolean StartNetworkListener(Context context){
        if(isStarted) return false;
        NetworkInfo networkInfo = ((ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if(networkInfo!=null)
        if(networkInfo.isConnected())
            networkState=NetworkState.ACTIVE;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        context.registerReceiver(receiver,filter);
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
    public static String IPAddress(Context context){
        if(networkState == NetworkState.ACTIVE) return wifiIpAddress(context);
        else return null;
    }

//    @Contract(pure = true)
    public static NetworkState GetCurrentNetworkState(){return networkState;}
}

