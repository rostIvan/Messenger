package trickyquestion.messenger.util.string_helper;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by Zen on 03.12.2017.
 */

public final class FixedStringConv {
    @NotNull
    static private java.lang.String fill(java.lang.String val, char filler, int sz){
        char[] array = new char[sz];
        Arrays.fill(array,filler);
        return new StringBuilder(new java.lang.String(array)).replace(0,val.length(),val).toString();
    }

    static private java.lang.String toDynamicSize(java.lang.String val, char filler){
        return val.substring(0,val.indexOf(filler));
    }
}
