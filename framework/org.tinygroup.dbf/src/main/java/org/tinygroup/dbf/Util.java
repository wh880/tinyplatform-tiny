package org.tinygroup.dbf;

/**
 * Created by luoguo on 2014/4/26.
 */
public class Util {


    public static int getUnsignedInt(byte[] bytes, int start, int length) {
        int value = 0;
        for (int i = 0; i < length; i++) {
            value += getIntValue(bytes[start + i], i);
        }
        return value;
    }

    public static int getIntValue(byte b, int bytePos) {
        int v = 1;
        for (int i = 0; i < bytePos; i++) {
            v = v * 256;
        }
        return getUnsignedInt(b) * v;
    }

    public static int getUnsignedInt(byte byteValue) {
        if (byteValue < 0) {
            return byteValue + 256;
        } else {
            return byteValue;
        }
    }
}
