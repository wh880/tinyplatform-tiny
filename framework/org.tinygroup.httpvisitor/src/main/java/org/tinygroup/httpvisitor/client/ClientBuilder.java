package org.tinygroup.httpvisitor.client;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.httpvisitor.config.HttpConfigTemplate;
import org.tinygroup.httpvisitor.config.HttpConfigTemplateContext;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;
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

	final static String NODE_NAME = "HTTPVISITOR_BEAN_NAME";
	
	private Map<String,Object> configMaps = new HashMap<String,Object>();
	
	private String templateId;

	protected abstract Builder self();
	
	ClientBuilder(String templateId){ 
		this.templateId = templateId;
	}

	/**
	 * 构建客户端实例
	 * 
	 * @return
	 */
	public synchronized ClientInterface build() {

		String beanName = getClientBeanName();
		if(beanName==null){
		   throw new HttpVisitorException("查找HTTP客户端实例Bean名称失败，请检查应用全局配置文件是否配置"+NODE_NAME+"节点");
		}
		try{
			BeanContainer<?> container = BeanContainerFactory
					.getBeanContainer(getClass().getClassLoader());
			
			ClientInterface clientInterface = null;
			if(templateId!=null){
				//优先从实例中加载bean对象
				ClientInstanceManager manager = container.getBean(ClientInstanceManager.DEFAULT_BEAN_NAME);
				clientInterface = manager.getClientInterface(templateId);
				boolean initTag = false;
				if(clientInterface==null){
					//如果管理器中没有，从bean容器加载
					clientInterface = container.getBean(beanName);
					initTag = clientInterface.allowMultiton();
				}
				if(initTag){
					HttpConfigTemplate template = getHttpConfigTemplate(clientInterface);
					if(template==null){
						throw new HttpVisitorException("查找HTTP通讯配置模板实例失败，请检查"+templateId+"的配置节点是否定义");
					}
					clientInterface.init(getContext(template));
					manager.registerClient(beanName, clientInterface); //注册实例
				}
				
			}else{
				clientInterface = container.getBean(beanName);
				clientInterface.init(getContext());
			}
			
			return clientInterface;
		}catch(Exception e){
			throw new HttpVisitorException("创建"+beanName+"的客户端实例失败",e);
		}
		
	}

	/**
	 * 从全局配置文件中查找bean名称
	 * @return
	 */
	private String getClientBeanName() {
		return ConfigurationUtil.getConfigurationManager().getConfiguration(NODE_NAME);
	}
	
	private HttpConfigTemplate getHttpConfigTemplate(ClientInterface clientInterface){
		return templateId!=null?clientInterface.getHttpTemplateManager().getHttpConfigTemplate(templateId):null;
	}

	protected Context getContext() {
		Context context = new ContextImpl(configMaps);
		return context;
	}
	
	protected Context getContext(HttpConfigTemplate template) {
		Context context = new HttpConfigTemplateContext(configMaps,template);
		return context;
	}

	public Builder timeToLive(long timeToLive) {
		configMaps.put(ClientConstants.CLIENT_KEEP_TIMEOUT, timeToLive);
		return self();
	}

	public Builder userAgent(String userAgent) {
		configMaps.put(ClientConstants.CLIENT_USER_AGENT, userAgent);
		return self();
	}

	public Builder verify(boolean verify) {
		configMaps.put(ClientConstants.CLIENT_ALLOW_VERIFY, verify);
		return self();
	}

	public Builder allowRedirects(boolean allowRedirects) {
		configMaps.put(ClientConstants.CLIENT_ALLOW_REDIRECT, allowRedirects);
		return self();
	}

	public Builder compress(boolean compress) {
		configMaps.put(ClientConstants.CLIENT_ALLOW_COMPRESS, compress);
		return self();
	}

	public Builder timeout(int timeout) {
		configMaps.put(ClientConstants.CLIENT_CONNECT_TIMEOUT, timeout);
		configMaps.put(ClientConstants.CLIENT_SOCKET_TIMEOUT, timeout);
		return self();
	}

	public Builder socketTimeout(int timeout) {
		configMaps.put(ClientConstants.CLIENT_SOCKET_TIMEOUT, timeout);
		return self();
	}

	public Builder connectTimeout(int timeout) {
		configMaps.put(ClientConstants.CLIENT_CONNECT_TIMEOUT, timeout);
		return self();
	}

	public Builder proxy(String host, int port, String proxyName,
			String password) {
		configMaps.put(ClientConstants.CLIENT_PROXY, new Proxy(host, port, proxyName, password));
		return self();
	}

	public Builder proxy(String host, int port) {
		configMaps.put(ClientConstants.CLIENT_PROXY, new Proxy(host, port));
		return self();
	}

	public Builder proxy(Proxy proxy) {
		configMaps.put(ClientConstants.CLIENT_PROXY, proxy);
		return self();
	}

	public Builder auth(String userName, String password) {
		configMaps.put(ClientConstants.CLIENT_CERT, new PasswordCert(userName, password));
		return self();
	}

	public Builder auth(PasswordCert cert) {
		configMaps.put(ClientConstants.CLIENT_CERT, cert);
		return self();
	}

	public Builder auth(String certPath, String password, String certType) {
		configMaps.put(ClientConstants.CLIENT_CERT, new KeyCert(certPath, password, certType));
		return self();
	}

	public Builder auth(KeyCert cert) {
		configMaps.put(ClientConstants.CLIENT_CERT, cert);
		return self();
	}
}
