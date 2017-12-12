package trickyquestion.messenger.p2p_protocol;

import android.widget.ImageView;

import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Zen on 03.12.2017.
 */

public class P2PFriend implements IFriend {

    private String name,ip;
    private UUID id;

    public P2PFriend(String name, UUID id, String ip){
        this.name = name; this.id = id; this.ip = ip;
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNetworkAddress() {
        return ip;
    }

    @Override
    public void updateData(IUser user) {
        if(id == user.getID()){
            name = user.getName();
            ip = user.getNetworkAddress();
        }
    }
}
