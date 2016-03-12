package org.tinygroup.httpvisitor.response;

import java.nio.charset.Charset;

import org.tinygroup.httpvisitor.Response;

public abstract class AbstractResponse implements Response{

	protected Charset charset;
	
	public AbstractResponse(){
		this.charset = Charset.forName("UTF-8");
	}
	
	public Response charset(String charset){
		this.charset = Charset.forName(charset);
		return self();
	}
	
	public Response charset(Charset charset){
		this.charset = charset;
		return self();
	}
	
	protected abstract Response self();
}
