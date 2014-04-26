package org.tinygroup.dbf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoguo on 2014/4/25.
 */
public abstract class DbfReader implements Reader {
    protected String encode = "GBK";
    private FileChannel fileChannel;
    protected Header header;
    protected List<Field> fields;
    private boolean recordRemoved;
    int position = 0;
    static Map<Integer, Class> readerMap = new HashMap<Integer, Class>();

    static {
        addReader(3, FoxproDBase3Reader.class);
    }

    public static void addReader(int type, Class clazz) {
        readerMap.put(type, clazz);
    }

    public static void addReader(int type, String className) throws ClassNotFoundException {
        readerMap.put(type, Class.forName(className));
    }

    public byte getType() {
        return 3;
    }

    public String getEncode() {
        return encode;
    }

    public Header getHeader() {
        return header;
    }

    public List<Field> getFields() {
        return fields;
    }


    public boolean isRecordRemoved() {
        return recordRemoved;
    }

    public static Reader parse(String dbfFile, String encode) throws IOException, IllegalAccessException, InstantiationException {
        return parse(new File(dbfFile), encode);
    }

    public static Reader parse(String dbfFile) throws IOException, IllegalAccessException, InstantiationException {
        return parse(new File(dbfFile), "GBK");
    }

    public static Reader parse(File dbfFile) throws IOException, IllegalAccessException, InstantiationException {
        return parse(dbfFile, "GBK");
    }

    public static Reader parse(File dbfFile, String encode) throws IOException, IllegalAccessException, InstantiationException {
        RandomAccessFile aFile = new RandomAccessFile(dbfFile, "r");
        FileChannel fileChannel = aFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        fileChannel.read(byteBuffer);
        byte type = byteBuffer.array()[0];
        Class<Reader> readerClass = readerMap.get((int)type);
        if (readerClass == null) {
            fileChannel.close();
            throw new IOException("不支持的文件类型[" + type + "]。");
        }
        DbfReader reader = (DbfReader) readerClass.newInstance();
        reader.setFileChannel(fileChannel);
        reader.readHeader();
        reader.readFields();
        return reader;
    }

    public void setFileChannel(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }


    protected abstract void readFields() throws IOException;

    public void moveBeforeFirst() throws IOException {
        position = 0;
        fileChannel.position(header.getHeaderLength());
    }

    /**
     * @param position 从1开始
     * @throws java.io.IOException
     */
    public void absolute(int position) throws IOException {
        if (position >= header.getRecordCount()) {
            throw new IOException("期望记录行数为" + (this.position + 1) + "，超过实际记录行数：" + header.getRecordCount() + "。");
        }
        this.position = position;
        fileChannel.position(header.getHeaderLength() + (position - 1) * header.getRecordLength());
    }

    protected abstract Field readField() throws IOException;

    protected abstract void readHeader() throws IOException;


    private void skipHeaderTerminator() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        readByteBuffer(byteBuffer);
    }

    public void close() throws IOException {
        fileChannel.close();
    }

    public void next() throws IOException {
        if (position >= header.getRecordCount()) {
            throw new IOException("期望记录行数为" + (position + 1) + "，超过实际记录行数：" + header.getRecordCount() + "。");
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        readByteBuffer(byteBuffer);
        this.recordRemoved = (byteBuffer.array()[0] == '*');
        for (Field field : fields) {
            read(field);
        }
        position++;
    }

    private void read(Field field) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(field.getLength());
        readByteBuffer(buffer);
        field.setStringValue(new String(buffer.array(), encode).trim());
        field.setBuffer(buffer);
    }

    protected void readByteBuffer(ByteBuffer byteBuffer) throws IOException {
        fileChannel.read(byteBuffer);
    }
}
