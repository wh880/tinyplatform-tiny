package org.tinygroup.dbf;

import java.io.IOException;

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
   public static String getFileType(int type) throws IOException {
        if (type == 0x02) {
            return "FoxBASE";
        } else if (type == 0x03) {
            return "FoxBASE +/Dbase III plus, no memo";
        } else if (type == 0x30) {
            return "Visual FoxPro";
        } else if (type == 0x31) {
            return "Visual FoxPro, autoincrement enabled";
        } else if (type == 0x43) {
            return "dBASE IV SQL table files, no memo";
        } else if (type == 0x63) {
            return "dBASE IV SQL system files, no memo";
        } else if (type == 0x83) {
            return "FoxBASE +/dBASE III PLUS, with memo";
        } else if (type == 0x8B) {
            return "dBASE IV with memo";
        } else if (type == 0xCB) {
            return "dBASE IV SQL table files, with memo";
        } else if (type == 0xF5) {
            return "FoxPro 2. x(or earlier) with memo";
        } else if (type == 0xFB) {
            return "FoxBASE";
        }
        throw new IOException("Not support file type:" + type);
    }
}
