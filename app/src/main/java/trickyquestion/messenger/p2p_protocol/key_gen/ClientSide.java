package trickyquestion.messenger.p2p_protocol.key_gen;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import static trickyquestion.messenger.util.string_helper.HexConv.bytesToHex;
import static trickyquestion.messenger.util.string_helper.HexConv.hexToByte;

/**
 * Created by Zen on 03.12.2017.
 */

public class ClientSide {
    private KeyAgreement clientKeyAgree;
    private PublicKey serverPubKey;
    private byte[] cipherKey;

    public String FirstPhase(String serverKey){
        String key = null;
        try {
            KeyFactory clientKeyFac = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(hexToByte(serverKey));
            serverPubKey = clientKeyFac.generatePublic(x509KeySpec);
            DHParameterSpec dhParamFromServerPubKey = ((DHPublicKey)serverPubKey).getParams();
            KeyPairGenerator clientKpairGen = KeyPairGenerator.getInstance("DH");
            clientKpairGen.initialize(dhParamFromServerPubKey);
            KeyPair clientKpair = clientKpairGen.generateKeyPair();
            clientKeyAgree = KeyAgreement.getInstance("DH");
            clientKeyAgree.init(clientKpair.getPrivate());
            byte[] clientPubKeyEnc = clientKpair.getPublic().getEncoded();
            key = bytesToHex(clientPubKeyEnc);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return key;
    }

    public void SecondPhase(){
        try {
            clientKeyAgree.doPhase(serverPubKey, true);
            cipherKey = clientKeyAgree.generateSecret();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public byte[] getCipherKey() {
        return cipherKey;
    }
}
