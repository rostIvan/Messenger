package trickyquestion.messenger.junit.util.java.string_helper;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertTrue;
import static trickyquestion.messenger.util.java.string_helper.HexConv.bytesToHex;
import static trickyquestion.messenger.util.java.string_helper.HexConv.hexToByte;

/**
 * Created by Nadiia Bogoslavets on 28.03.2018.
 */

public class HexConvTest {

    @Test
    public void bytesToHexCorrect () {
        String text = "0123456789abcdefghz";
        String res = bytesToHex(text.getBytes());
        assertTrue("hex conv don't work", res.equals("3031323334353637383961626364656667687A"));
    }

    @Test
    public void hexToByteCorrect () {
        String text = "3031323334353637383961626364656667687A";
        byte[] res = hexToByte(text);
        assertTrue("hex conv don't work", Arrays.equals("0123456789abcdefghz".getBytes(), res));
    }

    @Test
    public void bytesToHexHexToByteCorrect () {
        String text = "0123456789abcdefghz";
        byte[] res = hexToByte(bytesToHex(text.getBytes()));
        assertTrue("hex conv don't work",  Arrays.equals("0123456789abcdefghz".getBytes(), res));
    }
}
