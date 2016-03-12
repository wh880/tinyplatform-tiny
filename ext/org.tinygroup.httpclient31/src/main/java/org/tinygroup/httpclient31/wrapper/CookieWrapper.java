package org.tinygroup.httpclient31.wrapper;

import java.util.Date;

import org.tinygroup.httpvisitor.Cookie;

/**
 * Cookie包装类
 * @author yancheng11334
 *
 */
public class CookieWrapper implements Cookie {

	private org.apache.commons.httpclient.Cookie cookie;
	
	public CookieWrapper(org.apache.commons.httpclient.Cookie cookie){
		this.cookie = cookie;
	}
	public String getName() {
		return cookie.getName();
	}

	public String getValue() {
		return cookie.getValue();
	}

	public Date getExpiryDate() {
		return cookie.getExpiryDate();
	}

	public String getPath() {
		return cookie.getPath();
	}

	public String getDomain() {
		return cookie.getDomain();
	}

	public boolean isSecure() {
		return cookie.getSecure();
	}

}
