package org.tinygroup.httpvisitor.client;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.httpvisitor.Certifiable;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;
import org.tinygroup.httpvisitor.factory.ClientConstants;
import org.tinygroup.httpvisitor.struct.KeyCert;
import org.tinygroup.httpvisitor.struct.PasswordCert;
import org.tinygroup.httpvisitor.struct.Proxy;

/**
 * 抽象通讯客户端构造
 * 
 * @author yancheng11334
 * 
 * @param <Builder>
 */
public abstract class ClientBuilder<Builder extends ClientBuilder<Builder>>
		implements ClientBuilderInterface<Builder> {

	final static String defaultUserAgent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)";
	final static String NODE_NAME = "HTTPVISITOR_BEAN_NAME";
	final static int defaultTimeout = 10000;

	// how long http connection keep, in milliseconds. default -1, get from
	// server response
	protected long timeToLive = -1;

	// settings for client level, can not set/override in request level
	// if verify http certificate
	private boolean verify = true;

	// if enable compress response
	private boolean compress = true;
	private boolean allowRedirects = true;
	private String userAgent = defaultUserAgent;

	private int connectTimeout = defaultTimeout;
	private int socketTimeout = defaultTimeout;

	private Proxy proxy;
	private Certifiable cert;

	protected abstract Builder self();

	/**
	 * 构建客户端实例
	 * 
	 * @return
	 */
	public ClientInterface build() {

		String beanName = getClientBeanName();
		if(beanName==null){
		   throw new HttpVisitorException("查找HTTP客户端实例Bean名称失败，请检查应用全局配置文件是否配置"+NODE_NAME+"节点");
		}
		ClientInterface clientInterface = BeanContainerFactory
				.getBeanContainer(getClass().getClassLoader()).getBean(beanName);
		clientInterface.init(getContext());
		return clientInterface;
	}

	/**
	 * 从全局配置文件中查找bean名称
	 * @return
	 */
	private String getClientBeanName() {
		return ConfigurationUtil.getConfigurationManager().getConfiguration(NODE_NAME);
	}

	protected Context getContext() {
		Context context = new ContextImpl();

		context.put(ClientConstants.CLIENT_USER_AGENT, userAgent);
		context.put(ClientConstants.CLIENT_CONNECT_TIME, connectTimeout);
		context.put(ClientConstants.CLIENT_SOCKET_TIME, socketTimeout);
		context.put(ClientConstants.CLIENT_KEEP_TIME, timeToLive);
		context.put(ClientConstants.CLIENT_VERIFY, verify);
		context.put(ClientConstants.CLIENT_COMPRESS, compress);
		context.put(ClientConstants.CLIENT_ALLOW_REDIRECT, allowRedirects);
		context.put(ClientConstants.CLIENT_PROXY, proxy);
		context.put(ClientConstants.CLIENT_CERT, cert);

		return context;
	}

	public Builder timeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
		return self();
	}

	public Builder userAgent(String userAgent) {
		this.userAgent = userAgent;
		return self();
	}

	public Builder verify(boolean verify) {
		this.verify = verify;
		return self();
	}

	public Builder allowRedirects(boolean allowRedirects) {
		this.allowRedirects = allowRedirects;
		return self();
	}

	public Builder compress(boolean compress) {
		this.compress = compress;
		return self();
	}

	public Builder timeout(int timeout) {
		this.connectTimeout = this.socketTimeout = timeout;
		return self();
	}

	public Builder socketTimeout(int timeout) {
		this.socketTimeout = timeout;
		return self();
	}

	public Builder connectTimeout(int timeout) {
		this.connectTimeout = timeout;
		return self();
	}

	public Builder proxy(String host, int port, String proxyName,
			String password) {
		this.proxy = new Proxy(host, port, proxyName, password);
		return self();
	}

	public Builder proxy(String host, int port) {
		this.proxy = new Proxy(host, port);
		return self();
	}

	public Builder proxy(Proxy proxy) {
		this.proxy = proxy;
		return self();
	}

	public Builder auth(String userName, String password) {
		this.cert = new PasswordCert(userName, password);
		return self();
	}

	public Builder auth(PasswordCert cert) {
		this.cert = cert;
		return self();
	}

	public Builder auth(String certPath, String password, String certType) {
		this.cert = new KeyCert(certPath, password, certType);
		return self();
	}

	public Builder auth(KeyCert cert) {
		this.cert = cert;
		return self();
	}
}
