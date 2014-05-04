package org.tinygroup.dbf;

/**
 * Created by luoguo on 2014/4/26.
 */
public final class Util {

    private Util() {
    }

    public static final int MAX_MINI_UINT = 256;

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
            v = v * MAX_MINI_UINT;
        }
        return getUnsignedInt(b) * v;
    }

    public static int getUnsignedInt(byte byteValue) {
        if (byteValue < 0) {
            return byteValue + MAX_MINI_UINT;
        } else {
            return byteValue;
        }
    }

    public static Boolean getBooleanValue(String stringValue) {
        String value = stringValue.toLowerCase();
        if (value.equals("t") || value.equals("y")) {
            return true;
        }
        if (value.equals("f") || value.equals("n")) {
            return false;
        }
        return null;
    }
}
