package trickyquestion.messenger.p2p_protocol;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import trickyquestion.messenger.p2p_protocol.interfaces.IClient;

/**
 * Created by Zen on 11.10.2017.
 */

/**
 * Represent client side for protocol, simple binding service
 */
public final class ProtocolClientSide {
    static private P2PProtocolService.LocalBinder bind;
    static private boolean bound;
    static private IClient client;

    /**
     * Set client data(user name, id)
     * @param data client object
     */
    void SetClientData(IClient data){
        client = data;
    }

    /**
     * Service connector object for connecting P2PProtocolService
     */
    static private ServiceConnection sConn = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder binder) {
            //get binder
            bind = (P2PProtocolService.LocalBinder) binder;
            //seting client data;
            bind.SetClientData(client);
            //start service
            bind.Start();
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
    static boolean isServiceConnected(){
        return bound;
    }

    /**
     * Connect and start(if require) service
     * @param context from caller
     */
    static void ConnectService(Context context){
        //bind service
        context.bindService(new Intent(context, P2PProtocolService.class),sConn, Context.BIND_AUTO_CREATE);
        //start service
        context.startService(new Intent(context, P2PProtocolService.class));
    }

    /**
     * Service interface
     * @return binder which represent service or NULL if service not connected
     */
    static P2PProtocolService.LocalBinder ProtocolInterface(){
        //if service connected return bind
        if (bound) return bind; else return null;
    }
}

