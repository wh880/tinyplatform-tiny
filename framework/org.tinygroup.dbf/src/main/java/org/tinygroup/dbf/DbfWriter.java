package org.tinygroup.dbf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by wcg on 2014/7/7.
 */
public abstract class DbfWriter implements Writer {
	
    public static final int HEADER_END_CHAR = 13;
    private byte type;//记录类型
    private String encode = "GBK"; //默认编码
    private Header header; //头字节
    private String filename;
    protected List<Field> fields;//字段信息
    protected ByteArrayOutputStream bodybuffer = new ByteArrayOutputStream();
    protected ByteArrayOutputStream headbuffer = new ByteArrayOutputStream();
    private int postion = 0;
    
    public void setFilename(String filename) {
    	this.filename = filename;
    }
            
    public void setHeader(Header header) {
    	this.header = header;
    }
    
    public void setFields(List<Field> filelds) {
    	this.fields = filelds;
    }
    
    public void setType(byte type) {
    	this.type = type;
    }
    
    public String getEncode() {
    	return encode;
    }
    public void setEncode(String encode) {
    	this.encode = encode; 
    }
    
    public static DbfWriter generate(String filename,String encode)  throws IOException, IllegalAccessException, InstantiationException  {
        DbfWriter writer = new FoxproDBaseWriter();
        writer.setFilename(filename);
        
    	return writer;
    }
    
    public void write() throws IOException {
    	writeHeaders();
    	FileOutputStream fos = new FileOutputStream(new File(filename));
    	byte[] arr = bodybuffer.toByteArray();
    	headbuffer.write(arr,0,arr.length);
    	headbuffer.writeTo(fos);
    	headbuffer.close();
    	bodybuffer.close();
    	fos.close();
    }
    public abstract void writeFields(List<Field> f) throws IOException;
    
    public abstract void writeData(String...args) throws UnsupportedEncodingException, IOException;
    
    public abstract void writeHeaders() throws IOException;
    
    protected void next() {
		postion ++;
	}
    
    protected int  getPostion() {
		return postion;
	}

}
