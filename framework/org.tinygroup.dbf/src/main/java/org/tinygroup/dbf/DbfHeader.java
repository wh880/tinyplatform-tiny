package org.tinygroup.dbf;

import java.io.IOException;

/**
 * Created by luoguo on 2014/4/25.
 */
public class DbfHeader {
    private byte version;
    private int lastUpdate;
    private int recordCount;
    private int headerLength;
    private int recordLength;

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public int getRecordLength() {
        return recordLength;
    }

    public void setRecordLength(int recordLength) {
        this.recordLength = recordLength;
    }

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
