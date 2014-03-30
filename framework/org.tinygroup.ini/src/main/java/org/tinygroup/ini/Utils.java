package org.tinygroup.ini;

/**
 * Created by luoguo on 14-3-30.
 */
public class Utils {
    static String[] source = {"\t", "\r", "\n", ";", "=", ":"};
    static String[] encodeDest = {"\\\\t", "\\\\r", "\\\\n", "\\\\;", "\\\\=", "\\\\:"};
    static String[] decodeSource = {"[\\\\]t", "[\\\\]r", "[\\\\]n", "[\\\\];", "[\\\\]=", "[\\\\]:"};


    public static String encode(String string) {
        String str = string;
        for (int i = 0; i < source.length; i++) {
            str = str.replaceAll(source[i], encodeDest[i]);
        }
        return str;
    }

    public static String decode(String string) {
        String str = string;
        for (int i = 0; i < source.length; i++) {
            str = str.replaceAll(decodeSource[i], source[i]);
        }
        return str;
    }
}
