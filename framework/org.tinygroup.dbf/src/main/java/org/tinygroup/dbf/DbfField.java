package org.tinygroup.dbf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luoguo on 2014/4/25.
 */
public class DbfField {
    String name;
    int length;
    int decimal;
    char type;
    byte flag;
    int displacement;
    ByteBuffer buffer;
    DbfReader reader;

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public void setReader(DbfReader reader) {
        this.reader = reader;
    }

    void read() throws IOException {
        buffer = ByteBuffer.allocate(length);
        reader.fileChannel.read(buffer);
    }

    public String getStringValue() throws UnsupportedEncodingException {
        return new String(buffer.array(), reader.encode).trim();
    }

    public int getIntValue() throws UnsupportedEncodingException {
        return Integer.parseInt(getStringValue());
    }

    public double getDoubleValue() throws UnsupportedEncodingException {
        return Double.parseDouble(getStringValue());
    }

    public float getFloatValue() throws UnsupportedEncodingException {
        return Float.parseFloat(getStringValue());
    }

    public Date getDateValue() throws ParseException, UnsupportedEncodingException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.parse(getStringValue());
    }
}
