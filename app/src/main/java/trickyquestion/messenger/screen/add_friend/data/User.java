package trickyquestion.messenger.screen.add_friend.data;

import java.util.UUID;

import trickyquestion.messenger.p2p_protocol.interfaces.IUser;

public class User implements IUser {

    private String name;
    private UUID id;
    private String networkAddress;

    @Override
    public UUID getID() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNetworkAddress() {
        return networkAddress;
    }

    @Override
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public void setNetworkAddress(String newIP) {
        this.networkAddress = newIP;
    }
}
