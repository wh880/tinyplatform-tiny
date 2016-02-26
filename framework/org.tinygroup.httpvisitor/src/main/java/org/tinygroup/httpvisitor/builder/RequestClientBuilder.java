package org.tinygroup.httpvisitor.builder;

import java.nio.charset.Charset;
import java.util.Map;

import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Executable;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.client.ClientBuilder;
import org.tinygroup.httpvisitor.client.ClientBuilderInterface;
import org.tinygroup.httpvisitor.client.ClientInterface;
import org.tinygroup.httpvisitor.request.HttpRequestBuilder;
import org.tinygroup.httpvisitor.request.HttpRequestBuilderInterface;
import org.tinygroup.httpvisitor.struct.KeyCert;
import org.tinygroup.httpvisitor.struct.PasswordCert;
import org.tinygroup.httpvisitor.struct.Proxy;


/**
 * 结合HTTP构建器和客户端构建器
 * 
 * @author yancheng11334
 * 
 */
public abstract class RequestClientBuilder<R extends RequestClientBuilder<R, H>, H extends HttpRequestBuilder<H>>
		implements ClientBuilderInterface<R>, HttpRequestBuilderInterface<R>,Executable<Response> {
    
	public R timeToLive(long timeToLive) {
		findClientBuilder().timeToLive(timeToLive);
		return self();
	}
	
	public R userAgent(String userAgent) {
		findClientBuilder().userAgent(userAgent);
		return self();
	}

	public R verify(boolean verify) {
		findClientBuilder().verify(verify);
		return self();
	}

	public R allowRedirects(boolean allowRedirects) {
		findClientBuilder().allowRedirects(allowRedirects);
		return self();
	}

	public R compress(boolean compress) {
		findClientBuilder().compress(compress);
		return self();
	}

	public R timeout(int timeout) {
		findClientBuilder().timeout(timeout);
		return self();
	}

	public R socketTimeout(int timeout) {
		findClientBuilder().socketTimeout(timeout);
		return self();
	}

	public R connectTimeout(int timeout) {
		findClientBuilder().connectTimeout(timeout);
		return self();
	}
	
	public R proxy(String host, int port, String proxyName, String password) {
		findClientBuilder().proxy(host, port, proxyName, password);
		return self();
	}
	
	public R proxy(String host, int port) {
		findClientBuilder().proxy(host, port);
		return self();
	}
	
	public R proxy(Proxy proxy) {
		findClientBuilder().proxy(proxy);
		return self();
	}
	
	public R auth(String userName,String password) {
		findClientBuilder().auth(userName, password);
		return self();
	}
	
	public R auth(PasswordCert cert) {
		findClientBuilder().auth(cert);
		return self();
	}
	
	public R auth(String certPath, String password, String certType) {
		findClientBuilder().auth(certPath, password, certType);
		return self();
	}
	
	public R auth(KeyCert cert) {
		findClientBuilder().auth(cert);
		return self();
	}
	
	public R charset(String charset) {
		findRequsetBuilder().charset(charset);
		return self();
	}
	
	public R charset(Charset charset) {
		findRequsetBuilder().charset(charset);
		return self();
	}
	
	public R param(String name, Object value) {
		findRequsetBuilder().param(name, value);
		return self();
	}

	public R params(Map<String, Object> maps) {
		findRequsetBuilder().params(maps);
		return self();
	}

	public R header(String name, String value) {
		findRequsetBuilder().header(name, value);
		return self();
	}

	public R headers(Map<String, String> maps) {
		findRequsetBuilder().headers(maps);
		return self();
	}

	public R cookie(String domain, String name,
			String value) {
		findRequsetBuilder().cookie(domain, name, value);
		return self();
	}

	public R cookie(Cookie cookie) {
		findRequsetBuilder().cookie(cookie);
		return self();
	}

	public R cookies(Map<String, Cookie> cookies) {
		findRequsetBuilder().cookies(cookies);
		return self();
	}

	public Response execute() {
		ClientInterface client = findClientBuilder().build();
		Request request = findRequsetBuilder().execute();
		return client.execute(request);
	}

	protected abstract R self();
    protected abstract ClientBuilder findClientBuilder();
    protected abstract HttpRequestBuilder<H> findRequsetBuilder();
}
