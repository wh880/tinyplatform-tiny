/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.httpvisit.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.tinygroup.httpvisit.HttpVisitor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class HttpVisitorImpl implements HttpVisitor {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpVisitorImpl.class);
	HttpClient client;
	private String responseCharset = "UTF-8";
	private String requestCharset = "ISO-8859-1";
	int timeout = 30000;
	HttpState httpState = new HttpState();
	boolean authEnabled = false;
	List<String> authPrefs = null;
	private String proxyHost;
	private int proxyPort;
	private UsernamePasswordCredentials proxyUserPassword;



	public void setProxy(String proxyHost, int proxyPort, String userName,
			String passwrod) {
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		if (userName != null) {
			proxyUserPassword = new UsernamePasswordCredentials(
					"my-proxy-username", "my-proxy-password");
		}
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @param realm
	 * @param schema
	 * @param username
	 * @param password
	 */
	public void setBasicAuth(String host, int port, String realm,
			String schema, String username, String password) {
		httpState.setCredentials(new AuthScope("www.verisign.com", 443, realm,
				schema),
				new UsernamePasswordCredentials("username", "password"));
		authEnabled = true;
	}

	public void setBasicAuth(String host, int port, String username,
			String password) {
		setBasicAuth(host, port, username, password);
	}

	public void setAlternateAuth(String host, int port, String username,
			String password, List<String> schemaList) {
		httpState.setCredentials(new AuthScope("www.verisign.com", 443),
				new UsernamePasswordCredentials("username", "password"));
		authEnabled = true;

		client.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY,
				schemaList);
	}


	public String getRequestCharset() {
		return requestCharset;
	}

	public void setRequestCharset(String requestCharset) {
		this.requestCharset = requestCharset;
	}

	public String getResponseCharset() {
		return responseCharset;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void init() {
		client = new HttpClient(new MultiThreadedHttpConnectionManager());
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(timeout);
		client.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		client.setState(httpState);
		if (authPrefs != null) {
			client.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY,
					authPrefs);
		}
		if (proxyHost != null) {
			client.getHostConfiguration().setProxy(proxyHost, proxyPort);
			if (proxyUserPassword != null) {
				httpState.setProxyCredentials(new AuthScope(proxyHost,
						proxyPort), proxyUserPassword);
			}
		}
	}

	public String getUrl(String url, Map<String, ?> parameter) {
		try {
			StringBuffer sb = new StringBuffer(url);
			if (url.indexOf('?') < 0) {
				sb.append("?");
			}
			if (parameter != null) {
				for (String key : parameter.keySet()) {
					Object value = parameter.get(key);
					if (value.getClass().isArray()) {
						Object[] arrayValue = (Object[]) value;
						for (Object o : arrayValue) {
							appendParameter(sb, key, o);
						}
					} else {
						appendParameter(sb, key, value);
					}
				}
			}
			GetMethod get = new GetMethod(sb.toString());
			return execute(get);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	private void appendParameter(StringBuffer sb, String key, Object value)
			throws UnsupportedEncodingException {
		sb.append("&");
		sb.append(URLEncoder.encode(key, requestCharset));
		sb.append("=");
		sb.append(URLEncoder.encode(value.toString(), requestCharset));
	}

	public String postUrl(String url, Map<String, ?> parameter) {
		PostMethod post = new PostMethod(url);
		if (parameter != null) {
			for (String key : parameter.keySet()) {
				Object value = parameter.get(key);
				if (value.getClass().isArray()) {
					Object[] arrayValue = (Object[]) value;
					for (Object o : arrayValue) {
						post.addParameter(key, o.toString());
					}
				} else {
					post.setParameter(key, value.toString());
				}
			}
		}
		return execute(post);
	}

	String execute(HttpMethodBase method) {
		try {
			if(client==null){
				init();
			}
			logger.logMessage(LogLevel.DEBUG, "正在访问地址:{}", method.getURI()
					.toString());
			if (!requestCharset.equals("ISO-8859-1")) {
				method.addRequestHeader("Content-Type", "text/html; charset="
						+ requestCharset);
			}
			method.setDoAuthentication(authEnabled);
			int iGetResultCode = client.executeMethod(method);
			if (iGetResultCode == HttpStatus.SC_OK) {
				logger.logMessage(LogLevel.DEBUG, "结果成功返回。");
				String strGetResponseBody = new String(
						method.getResponseBody(), responseCharset);
				return strGetResponseBody;
			}
			logger.logMessage(LogLevel.ERROR, "结果返回失败，原因：{}", method
					.getStatusLine().toString());
			throw new RuntimeException(method.getStatusLine().toString());
		} catch (Exception e) {
			logger.logMessage(LogLevel.DEBUG, "结果返回失败，原因：{}", e.getMessage());
			throw new RuntimeException(e);
		} finally {
			method.releaseConnection();
		}
	}

	public void setResponseCharset(String charset) {
		this.responseCharset = charset;
	}

	public HttpClient getHttpClient() {
		return client;
	}

	public void addCookie(Cookie cookie) {
		httpState.addCookie(cookie);
	}

	public Cookie[] getCookies() {
		return httpState.getCookies();
	}

	public String postSoap(String url, String soapAction, String xmlEntiry) {
		PostMethod post = new PostMethod(url);
		try {
			RequestEntity entity = new StringRequestEntity(xmlEntiry, null,
					null);
			post.setRequestEntity(entity);
			if (soapAction != null) {
				post.setRequestHeader("SOAPAction", soapAction);
			}
			return execute(post);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			post.releaseConnection();
		}
	}

	public String postSoap(String url, String xmlEntiry) {
		return postSoap(url, null, xmlEntiry);
	}

	public String postXml(String url, String xmlEntiry) {
		PostMethod post = new PostMethod(url);
		try {
			post.setDoAuthentication(authEnabled);
			RequestEntity entity = new StringRequestEntity(xmlEntiry, null,
					null);
			post.setRequestEntity(entity);
			return execute(post);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			post.releaseConnection();
		}
	}

	public void addCookie(Cookie[] cookies) {
		httpState.addCookies(cookies);
	}

	public HttpState getHttpState() {
		return client.getState();
	}
}