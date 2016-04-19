package org.tinygroup.weixinhttpclient3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.methods.multipart.PartBase;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.tinygroup.vfs.FileObject;

/**
 * 扩展HttpClient的Part接口，支持处理FileObject
 * @author yancheng11334
 *
 */
public class FileObjectPart extends PartBase{

	 /** Default content encoding of file attachments. */
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    /** Default charset of file attachments. */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    /** Default transfer encoding of file attachments. */
    public static final String DEFAULT_TRANSFER_ENCODING = "binary";

    /** Attachment's file name */
    protected static final String FILE_NAME = "; filename=";

    /** Attachment's file name as a byte array */
    private static final byte[] FILE_NAME_BYTES = 
        EncodingUtil.getAsciiBytes(FILE_NAME);

    /** Source of the file part. */
    private FileObject source;
    
    public FileObjectPart(FileObject partSource){
    	this(partSource.getFileName(),partSource,null,null);
    }
    
    public FileObjectPart(FileObject partSource, String contentType, String charset) {
    	this(partSource.getFileName(),partSource,contentType,charset);
    }
    
    public FileObjectPart(String name, FileObject partSource, String contentType, String charset) {
        
        super(
            name, 
            contentType == null ? DEFAULT_CONTENT_TYPE : contentType, 
            charset == null ? "ISO-8859-1" : charset, 
            DEFAULT_TRANSFER_ENCODING
        );

        if (partSource == null) {
            throw new IllegalArgumentException("Source may not be null");
        }
        this.source = partSource;
    }
    
    protected void sendDispositionHeader(OutputStream out) 
    throws IOException {
		out.write(CONTENT_DISPOSITION_BYTES);
        out.write(QUOTE_BYTES);
        out.write(EncodingUtil.getAsciiBytes(getName()));
        out.write(QUOTE_BYTES);
        if (source != null) {
            out.write(FILE_NAME_BYTES);
            out.write(QUOTE_BYTES);
            out.write(EncodingUtil.getAsciiBytes(source.getFileName()));
            out.write(QUOTE_BYTES);
        }
    }

	protected long lengthOfData() throws IOException {
		return source.getSize();
	}

	protected void sendData(OutputStream out) throws IOException {
        if (lengthOfData() == 0) {
            return;
        }
        
        byte[] tmp = new byte[4096];
        InputStream instream = source.getInputStream();
        try {
            int len;
            while ((len = instream.read(tmp)) >= 0) {
                out.write(tmp, 0, len);
            }
        } finally {
            instream.close();
        }
    }

}
