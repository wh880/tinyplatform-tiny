package org.tinygroup.httpvisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.context.Context;
import org.tinygroup.httpvisitor.client.ClientConstants;
import org.tinygroup.httpvisitor.config.HttpConfigTemplate;
import org.tinygroup.httpvisitor.config.HttpConfigTemplateContext;
import org.tinygroup.httpvisitor.manager.HttpTemplateManager;
import org.tinygroup.httpvisitor.struct.KeyCert;
import org.tinygroup.httpvisitor.struct.PasswordCert;
import org.tinygroup.httpvisitor.struct.Proxy;
import org.tinygroup.tinyrunner.Runner;

/**
 * 测试HTTP通讯配置模板
 * @author yancheng11334
 *
 */
public class HttpTemplateTest extends TestCase{

	private HttpTemplateManager manager;
	
	protected void setUp() {
		Runner.init("application.xml", new ArrayList<String>());
		manager = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean("httpTemplateManager");
	}
	
	public void testHttpTemplateManager(){
		HttpConfigTemplate t1 = new HttpConfigTemplate("t1");
		assertNull(manager.getHttpConfigTemplate("t1"));
		manager.addHttpConfigTemplate(t1);
		assertNotNull(manager.getHttpConfigTemplate("t1"));
		manager.removeHttpConfigTemplate("t1");
		assertNull(manager.getHttpConfigTemplate("t1"));
	}
	
	
	public void testHttpConfigTemplate(){
		HttpConfigTemplate template = manager.getHttpConfigTemplate("test1");
		assertNotNull(template);
		
		assertEquals(5000, template.getClientParamter(ClientConstants.CLIENT_CONNECT_TIMEOUT));
		assertEquals(10000, template.getClientParamter(ClientConstants.CLIENT_SOCKET_TIMEOUT));
		assertEquals(86400, template.getClientParamter(ClientConstants.CLIENT_KEEP_TIMEOUT));
		
		assertEquals(false, template.getClientParamter(ClientConstants.CLIENT_ALLOW_COMPRESS));
		assertEquals(false, template.getClientParamter(ClientConstants.CLIENT_ALLOW_REDIRECT));
		assertEquals(true, template.getClientParamter(ClientConstants.CLIENT_ALLOW_VERIFY));
		
		assertEquals("xxxx", template.getClientParamter("unknown1"));
		assertEquals("yyyy", template.getClientParamter("unknown2"));
		assertEquals(null, template.getClientParamter("unknown3"));
		assertEquals("bbbb", template.getClientParamter("unknown3","bbbb"));
		
		assertEquals("text/html, application/xhtml+xml, */*", template.getHeaderParamter("Accept"));
		assertEquals("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)", template.getHeaderParamter("User-Agent"));
	}
	

    public void testHttpConfigTemplateContext(){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("key1", "key+");
    	map.put(ClientConstants.CLIENT_SOCKET_TIMEOUT, 5555);
    	HttpConfigTemplate template = manager.getHttpConfigTemplate("test1");
		assertNotNull(template);
		
		Context context = new HttpConfigTemplateContext(map,template);
		
		assertEquals(true, context.exist("key1"));
		assertEquals(true, context.exist(ClientConstants.CLIENT_KEEP_TIMEOUT));
		
		assertEquals(map.get("key1"), context.get("key1"));  //从原始context的itemMap获取
		assertEquals(template.getClientParamter(ClientConstants.CLIENT_KEEP_TIMEOUT), context.get(ClientConstants.CLIENT_KEEP_TIMEOUT));  //从HttpConfigTemplate的获取
		assertEquals(map.get(ClientConstants.CLIENT_SOCKET_TIMEOUT), context.get(ClientConstants.CLIENT_SOCKET_TIMEOUT));  
	}
	
	public void testCert(){
		HttpConfigTemplate template = manager.getHttpConfigTemplate("cert1");
		assertNotNull(template);
		
		PasswordCert cert1 = (PasswordCert) template.getClientParamter(ClientConstants.CLIENT_CERT);
		assertEquals("admin",cert1.getUserName());
		assertEquals("123",cert1.getPassword());
		
		template = manager.getHttpConfigTemplate("cert2");
		assertNotNull(template);
		
		KeyCert cert2 = (KeyCert) template.getClientParamter(ClientConstants.CLIENT_CERT);
		assertEquals("/opt/user.p12",cert2.getCertPath());
		assertEquals("456",cert2.getPassword());
		assertEquals("PKS12",cert2.getCertType());
	}
	
	public void testProxy(){
		HttpConfigTemplate template = manager.getHttpConfigTemplate("proxy1");
		assertNotNull(template);
		
		Proxy proxy1 = (Proxy) template.getClientParamter(ClientConstants.CLIENT_PROXY);
		assertEquals("112.45.210.83",proxy1.getHost());
		assertEquals(8080,proxy1.getPort());
		
		template = manager.getHttpConfigTemplate("proxy2");
		assertNotNull(template);
		
		Proxy proxy2 = (Proxy) template.getClientParamter(ClientConstants.CLIENT_PROXY);
		assertEquals("112.45.210.83",proxy2.getHost());
		assertEquals(8080,proxy2.getPort());
		assertEquals("root",proxy2.getProxyName());
		assertEquals("123456",proxy2.getPassword());
	}
	
	
}
