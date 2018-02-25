package trickyquestion.messenger.p2p_protocol.objects;

import java.util.Date;
import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

/**
 * Created by Subaru on 17.12.2017.
 */

public class OUser implements IUser {
    private UUID ID;
    private String UName;
    private String IP;
    //TTL is time to end which user data is valid
    private Date TTL;

    public OUser(UUID ID, String Name, String IP, Date TTL) {
        this.ID = ID;
        this.UName = Name;
        this.IP = IP;
        this.TTL = TTL;
    }

    public UUID getID()    {return ID;}
    public String getName(){return UName;}
    public String getNetworkAddress()  {return IP;}

    @Override
    public void setName(String newName) {
        this.UName = newName;
    }

    @Override
    public void setNetworkAddress(String newIP) {
        IP = newIP;
    }

    public Date getTTL()   {return TTL;}

    public void setTTL(Date NewTTL){TTL = NewTTL;}

    public boolean equal(OUser second){
        if((this.ID.equals(second.ID)) && (this.IP.equals(second.IP))) {
            return true;
        }
        else return false;
    }

    public boolean equalUserName(OUser second) {
        return ((this.UName.equals(second.UName)));
    }
}
