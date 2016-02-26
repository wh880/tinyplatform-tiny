package org.tinygroup.httpvisitor.struct;

import org.tinygroup.httpvisitor.Certifiable;

/**
 * 口令认证
 * 
 * @author yancheng11334
 * 
 */
public class PasswordCert implements Certifiable {

	private String userName;
	private String password;

	public PasswordCert(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "PasswordCert [userName=" + userName + ", password=" + password
				+ "]";
	}
	
}
