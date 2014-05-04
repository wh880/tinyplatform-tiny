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

    public static final int HEADER_LENGTH = 32;
    public static final int FIELD_LENGTH = 32;
    public static final int NAME_LENGTH = 11;
    public static final int RECORD_COUNT_POSITION = 3;
    public static final int RECORD_COUNT_LENGTH = 4;
    public static final int HEADER_LENGTH_POSITION = 7;
    public static final int HEADER_LENGTH_LENGTH = 2;
    public static final int RECORD_LENGTH_POSITION = 9;
    public static final int RECORD_LENGTH_LENGTH = 2;
    public static final int FIELD_LENGTH_POSITON = 16;
    public static final int FIELD_DECIMAL_POSITON = 17;
    public static final int DISPLACEMENT_POSITION = 12;
    public static final int DISPLACEMENT_LENGTH = 4;
    public static final int FIELD_FLAG_POSITION = 18;

    protected void readFields() throws IOException {
        fields = new ArrayList<Field>();
        for (int i = 0; i < (header.getHeaderLength() - HEADER_LENGTH - 1) / FIELD_LENGTH; i++) {
            fields.add(readField());
        }
    }

    protected Field readField() throws IOException {
        Field field = new Field();
        ByteBuffer byteBuffer = ByteBuffer.allocate(FIELD_LENGTH);
        readByteBuffer(byteBuffer);
        byte[] bytes = byteBuffer.array();
        field.setName(new String(bytes, 0, NAME_LENGTH, encode).trim().split("\0")[0]);
        field.setType((char) bytes[NAME_LENGTH]);
        field.setDisplacement(Util.getUnsignedInt(bytes, DISPLACEMENT_POSITION, DISPLACEMENT_LENGTH));
        field.setLength(Util.getUnsignedInt(bytes, FIELD_LENGTH_POSITON, 1));
        field.setDecimal(Util.getUnsignedInt(bytes, FIELD_DECIMAL_POSITON, 1));
        field.setFlag(bytes[FIELD_FLAG_POSITION]);
        return field;
    }

    protected void readHeader() throws IOException {
        header = new Header();
        ByteBuffer byteBuffer = ByteBuffer.allocate(HEADER_LENGTH - 1);
        readByteBuffer(byteBuffer);
        byte[] bytes = byteBuffer.array();
        header.setLastUpdate((Util.getUnsignedInt(bytes, 0, 1) + 1900) * 10000 + Util.getUnsignedInt(bytes, 1, 1) * 100 + Util.getUnsignedInt(bytes, 2, 1));
        header.setRecordCount(Util.getUnsignedInt(bytes, RECORD_COUNT_POSITION, RECORD_COUNT_LENGTH));
        header.setHeaderLength(Util.getUnsignedInt(bytes, HEADER_LENGTH_POSITION, HEADER_LENGTH_LENGTH));
        header.setRecordLength(Util.getUnsignedInt(bytes, RECORD_LENGTH_POSITION, RECORD_LENGTH_LENGTH));
    }
}
