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
    private String encode = "GBK"; //默认编码
    private String filename;
    protected List<Field> fields;//字段信息
    protected ByteArrayOutputStream bodybuffer = new ByteArrayOutputStream();
    protected ByteArrayOutputStream headbuffer = new ByteArrayOutputStream();
    private int postion = 0;
    
    public void setFilename(String filename) {
    	this.filename = filename;
    }
    
    public void setFields(List<Field> filelds) {
    	this.fields = filelds;
    }
    
    public String getEncode() {
    	return encode;
    }
    public void setEncode(String encode) {
    	this.encode = encode; 
    }
    
    public static Writer generate(String filename,String encode)   {
        DbfWriter writer = new FoxproDBaseWriter();
        writer.setFilename(filename);
    	return writer;
    }
    
    public void save() throws IOException {
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
    
    public abstract void writeRecord(String...args) throws UnsupportedEncodingException, IOException,NullPointerException;
    
    protected abstract void writeHeaders() throws IOException;
    
    protected void next() {
		postion ++;
	}
    
    protected int  getPostion() {
		return postion;
	}

}
