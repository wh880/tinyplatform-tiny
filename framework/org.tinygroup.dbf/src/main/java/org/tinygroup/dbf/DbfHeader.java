package org.tinygroup.dbf;

import java.io.IOException;

/**
 * Created by luoguo on 2014/4/25.
 */
public class DbfHeader {
    byte version;
    int lastUpdate;
    int recordCount;
    int headerLength;
    int recordLength;
    String getFileType() throws IOException {
        if (version == 0x02) {
            return "FoxBASE";
        }
        if (version == 0x03) {
            return "FoxBASE +/Dbase III plus, no memo";
        }
        if (version == 0x30) {
            return "Visual FoxPro";
        }
        if (version == 0x31) {
            return "Visual FoxPro, autoincrement enabled";
        }

        if (version == 0x43) {
            return "dBASE IV SQL table files, no memo";
        }

        if (version == 0x63) {
            return "dBASE IV SQL system files, no memo";
        }
        if (version == 0x83) {
            return "FoxBASE +/dBASE III PLUS, with memo";
        }
        if (version == 0x8B) {
            return "dBASE IV with memo";
        }
        if (version == 0xCB) {
            return "dBASE IV SQL table files, with memo";
        }
        if (version == 0xF5) {
            return "FoxPro 2. x(or earlier) with memo";
        }
        if (version == 0xFB) {
            return "FoxBASE";
        }
        throw new IOException("Not support file type:" + version);

    }
}
