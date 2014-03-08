package org.tinygroup.dbrouter.config;

import org.tinygroup.commons.tools.EqualsUtil;
import org.tinygroup.commons.tools.HashCodeUtil;

public class DataSourceConfigBean {

	/**
	 * 驱动类名
	 */
	String driver;
	/**
	 * 连接URL
	 */
	String url;
	/**
	 * 用户名
	 */
	String userName;
	/**
	 * 密码
	 */
	String password;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int hashCode() {
		return HashCodeUtil.reflectionHashCode(this);
	}

	public boolean equals(Object obj) {
		return EqualsUtil.reflectionEquals(this, obj);
	}

}
