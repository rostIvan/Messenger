package trickyquestion.messenger.p2p_protocol.key_gen;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;

import static trickyquestion.messenger.util.string_helper.HexConv.bytesToHex;
import static trickyquestion.messenger.util.string_helper.HexConv.hexToByte;

/**
 * Created by Zen on 03.12.2017.
 */

public class ServerSide {
    private KeyAgreement ServerKeyAgrre;
    private byte[] cipherKey;

    private int keySize;

    public ServerSide(int keySize){
        this.keySize = keySize;
    }

    public String FirstPhase(){
        String key = null;
        try {
            KeyPairGenerator ServerKPair = KeyPairGenerator.getInstance("DH");
            ServerKPair.initialize(1024);
            KeyPair ServerKpair = ServerKPair.generateKeyPair();
            ServerKeyAgrre = KeyAgreement.getInstance("DH");
            ServerKeyAgrre.init(ServerKpair.getPrivate());
            byte[] serverPubKeyEnc = ServerKpair.getPublic().getEncoded();
            key = bytesToHex(serverPubKeyEnc);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return key;
    }

    public void SecondPhase(String pubKey){
        try {
            KeyFactory serverKeyFac = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(hexToByte(pubKey));
            PublicKey clientPubKey = serverKeyFac.generatePublic(x509KeySpec);
            ServerKeyAgrre.doPhase(clientPubKey, true);
            cipherKey = ServerKeyAgrre.generateSecret();
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public byte[] getCipherKey() {
        return cipherKey;
    }
}
