package org.tinygroup.dbf.impl;

import org.tinygroup.dbf.DbfReader;
import org.tinygroup.dbf.Field;
import org.tinygroup.dbf.Header;
import org.tinygroup.dbf.Util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by luoguo on 2014/4/25.
 */
public class FoxproDBaseReader extends DbfReader {
    protected void readFields() throws IOException {
        fields = new ArrayList<Field>();
        for (int i = 0; i < (header.getHeaderLength() - 32 - 1) / 32; i++) {
            fields.add(readField());
        }
    }

    protected Field readField() throws IOException {
        Field field = new Field();
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        readByteBuffer(byteBuffer);
        byte[] bytes = byteBuffer.array();
        field.setName(new String(bytes, 0, 11, encode).trim().split("\0")[0]);
        field.setType((char) bytes[11]);
        field.setDisplacement(Util.getUnsignedInt(bytes, 12, 4));
        field.setLength(Util.getUnsignedInt(bytes, 16, 1));
        field.setDecimal(Util.getUnsignedInt(bytes, 17, 1));
        field.setFlag(bytes[18]);
        return field;
    }

    protected void readHeader() throws IOException {
        header = new Header();
        ByteBuffer byteBuffer = ByteBuffer.allocate(31);
        readByteBuffer(byteBuffer);
        byte[] bytes = byteBuffer.array();
        header.setLastUpdate((Util.getUnsignedInt(bytes, 0, 1) + 1900) * 10000 + Util.getUnsignedInt(bytes, 1, 1) * 100 + Util.getUnsignedInt(bytes, 2, 1));
        header.setRecordCount(Util.getUnsignedInt(bytes, 3, 4));
        header.setHeaderLength(Util.getUnsignedInt(bytes, 7, 2));
        header.setRecordLength(Util.getUnsignedInt(bytes, 9, 2));
    }
}
