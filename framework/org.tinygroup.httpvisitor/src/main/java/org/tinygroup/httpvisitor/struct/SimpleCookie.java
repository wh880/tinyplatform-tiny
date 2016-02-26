package org.tinygroup.httpvisitor.struct;

import java.util.Date;

import org.tinygroup.httpvisitor.Cookie;

/**
 * 简易cookie值对象
 * @author yancheng11334
 *
 */
public class SimpleCookie implements Cookie{

	private String name;
	
	private String value;
	
	private Date expiryDate;
	
	private String path;
	
	private String domain;
	
	private boolean secure;
	
	public SimpleCookie(){
		super();
	}

	public SimpleCookie(String name, String value, Date expiryDate,
			String path, String domain, boolean secure) {
		super();
		this.name = name;
		this.value = value;
		this.expiryDate = expiryDate;
		this.path = path;
		this.domain = domain;
		this.secure = secure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

}
