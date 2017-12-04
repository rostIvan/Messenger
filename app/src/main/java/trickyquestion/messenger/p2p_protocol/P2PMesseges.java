package trickyquestion.messenger.p2p_protocol;

import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import trickyquestion.messenger.network.Network;
import trickyquestion.messenger.p2p_protocol.interfaces.IHost;
import trickyquestion.messenger.p2p_protocol.interfaces.IFriend;
import trickyquestion.messenger.util.Constants;
import trickyquestion.messenger.util.preference.NetworkPreference;
import trickyquestion.messenger.util.string_helper.FixedString;
import trickyquestion.messenger.util.string_helper.HexConv;

/**
 * Created by Zen on 04.12.2017.
 */

public class P2PMesseges {
    private NetworkPreference serviceCfg;
    private IHost host;
    private Context context;

    P2PMesseges(NetworkPreference serviceCfg, IHost host, Context context){
        this.serviceCfg = serviceCfg;
        this.host = host;
        this.context = context;
    }

    public void SendMsg(IFriend target, String msg){
        try {
            SecretKeySpec aesKey = new SecretKeySpec(target.encKey(), 0, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] data = cipher.doFinal(msg.getBytes());
            String fixedMsg = FixedString.fill(HexConv.bytesToHex(data),'$', Constants.MAX_MSG_SIZE);
            String packet = "P2PProtocol:MSG:" + host.getID().toString() + ":" + target.getID().toString() + ":" + fixedMsg + ":P2PProtocol";
            byte[] buff = new byte[packet.length()];
            InetSocketAddress destAddress = new InetSocketAddress(
                    target.getNetworkAddress(),
                    serviceCfg.getMsgPort()
            );
            DatagramPacket datagramPacket = new DatagramPacket(buff, packet.length() + 69, destAddress);
            datagramPacket.setData(packet.getBytes());
            DatagramSocket socket = new DatagramSocket(
                    serviceCfg.getMsgPort(),
                    InetAddress.getByName(Network.IPAddress(context)));
            socket.send(datagramPacket);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
