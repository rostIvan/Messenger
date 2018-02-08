package trickyquestion.messenger.util.java.string_helper;

import android.support.annotation.NonNull;

import java.util.Arrays;

/**
 * Created by Zen on 03.12.2017.
 */

public final class FixedString {
    @NonNull
    static public String fill(String val, char filler, int sz){
        char[] array = new char[sz];
        Arrays.fill(array,filler);
        return new StringBuilder(new String(array)).replace(0,val.length(),val).toString();
    }

    static public String toDynamicSize(String val, char filler){
        return val.substring(0,val.indexOf(filler));
    }
}
