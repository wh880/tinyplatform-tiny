package org.tinygroup.httpclient31.body;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.methods.multipart.PartSource;

public class InputStreamPartSource implements PartSource{

	private InputStream input;
	
	private String fileName;
	
	public InputStreamPartSource(String fileName,InputStream input) {
		this.fileName = fileName;
		this.input = input;
	}
	
	public long getLength() {
		try {
			return input.available();
		} catch (IOException e) {
			//忽略异常
			return 0;
		}
	}

	public String getFileName() {
		return fileName;
	}

	public InputStream createInputStream() throws IOException {
		return input;
	}

}
