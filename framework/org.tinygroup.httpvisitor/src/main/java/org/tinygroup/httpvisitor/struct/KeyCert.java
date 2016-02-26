package org.tinygroup.httpvisitor.struct;

import org.tinygroup.httpvisitor.Certifiable;

/**
 * 秘钥认证
 * @author yancheng11334
 *
 */
public class KeyCert implements Certifiable {

	/**
	 * 证书路径
	 */
	private String certPath;
	
	/**
	 * 验证密码
	 */
	private String password;
	
	/**
	 * 证书类型
	 */
	private String certType;

	public String getCertPath() {
		return certPath;
	}

	public String getPassword() {
		return password;
	}

	public String getCertType() {
		return certType;
	}

	public KeyCert(String certPath, String password, String certType) {
		super();
		this.certPath = certPath;
		this.password = password;
		this.certType = certType;
	}

	@Override
	public String toString() {
		return "KeyCert [certPath=" + certPath + ", password=" + password
				+ ", certType=" + certType + "]";
	}
	
}
