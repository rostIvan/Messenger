package trickyquestion.messenger.p2p_protocol.key_gen;

/**
 * Created by Subaru on 08.12.2017.
 */

public class SelfKey {
    byte[] key;

    public SelfKey(int keySize){
        ServerSide serverSide = new ServerSide(keySize);
        ClientSide clientSide = new ClientSide();
        serverSide.SecondPhase(clientSide.FirstPhase(serverSide.FirstPhase()));
        key = serverSide.getCipherKey();
    }

    public byte[] getKey() {
        return key;
    }
}
