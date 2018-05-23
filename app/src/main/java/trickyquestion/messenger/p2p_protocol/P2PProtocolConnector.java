package trickyquestion.messenger.p2p_protocol;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by Zen on 11.10.2017.
 */

/**
 * Represent client side for protocol, simple binding service
 */
public class P2PProtocolConnector {
    private P2PProtocolConnector(){
        throw new IllegalStateException("Cant create P2PProtocolConnector class");
    }

    private static P2PProtocolService.LocalBinder bind;
    private static boolean bound;

    /**
     * Service connector object for connecting P2PProtocolService
     */
    private static ServiceConnection sConn = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder binder) {
            //get binder
            bind = (P2PProtocolService.LocalBinder) binder;
            //start service
            bind.start();
            //signalize that service connected
            bound = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            //signalize that service disconneted
            bound = false;
        }
    };

    /**
     * @return service status
     */
    public static boolean isServiceConnected() {
        return bound;
    }

    /**
     * Connect and start(if require) service
     *
     * @param context from caller
     */
    private static void connectService(Context context) {
        //bind service
        context.bindService(new Intent(context, P2PProtocolService.class), sConn, Context.BIND_AUTO_CREATE);
        //start service
        context.startService(new Intent(context, P2PProtocolService.class));
    }

    /**
     * Service interface
     *
     * @return binder which represent service or NULL if service not connected
     */
    public static P2PProtocolService.LocalBinder protocolInterface() {
        //if service connected return bind
        return bind;
    }
    /**
    * Try start service if service not work
    */
    public static void tryStart(Context context) {
        if (!bound) {
            connectService(context);
        }
    }
}