package trickyquestion.messenger.util.string_helper;

import java.lang.*;

/**
 * Created by Zen on 03.12.2017.
 */

public final class HexConv {    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static java.lang.String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new java.lang.String(hexChars);
    }
    public static byte[] hexToByte(java.lang.String str){
        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
                    + Character.digit(str.charAt(i+1), 16));
        }
        return data;
    }
}
