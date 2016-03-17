package org.tinygroup.httpclient451.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 简化验证逻辑，允许非严格的证书通过
 * @author yancheng11334
 *
 */
public class SimpleHostnameVerifier implements HostnameVerifier{

	public boolean verify(String hostname, SSLSession session) {
		return true;
	}

}
