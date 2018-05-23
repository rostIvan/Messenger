package trickyquestion.messenger.p2p_protocol.objects;

import java.util.Date;
import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Subaru on 17.12.2017.
 */

public class OUser implements IUser {
    private UUID id;
    private String name;
    private String ip;
    //TTL is time to end which user data is valid
    private Date ttl;

    public OUser(UUID id, String name, String ip, Date ttl) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.ttl = ttl;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNetworkAddress() {
        return ip;
    }

    public Date getTTL() {
        return ttl;
    }

    @Override
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public void setNetworkAddress(String newIP) {
        ip = newIP;
    }

    public void setTTL(Date newTTL) {
        ttl = newTTL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OUser) {
            OUser second = (OUser) obj;
            return (this.id.equals(second.id)) && (this.ip.equals(second.ip));
        }
        return super.equals(obj);
    }

    public boolean equalUserName(OUser second) {
        return (this.name.equals(second.name));
    }
}
