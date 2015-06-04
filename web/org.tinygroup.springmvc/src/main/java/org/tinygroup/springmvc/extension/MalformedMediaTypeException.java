package org.tinygroup.springmvc.extension;

/**
 * 
 * @author kevin.luy
 * @since 2014年7月29日
 */
public class MalformedMediaTypeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MalformedMediaTypeException(String msg, Throwable e) {
		super(msg, e);
	}

}
