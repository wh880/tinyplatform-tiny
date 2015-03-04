package org.tinygroup.urlrestful;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class UrlRestfulTest extends TestCase {

	private static final String TEXT_HTML = "text/html";
	private static final String TEXT_JSON="text/json";
	private static final String GET="get";
	private static final String POST="post";
	private static final String PUT="put";
	private static final String DELETE="delete";
	private UrlRestfulManager urlRestfulManager;

	protected void setUp() throws Exception {
		super.setUp();
		AbstractTestUtil.init(null, true);
		urlRestfulManager = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader()).getBean("urlRestfulManager");
	}

	public void testRestFul() {

		String path="/users/12";
		RestfulContext restfulContext= urlRestfulManager.getUrlMappingWithRequet(path, GET, TEXT_HTML);
		assertEquals(restfulContext.getMappingUrl(), "queryUsersTiny.servicepage");
		assertEquals("12", restfulContext.getVariableMap().get("id"));
		restfulContext=urlRestfulManager.getUrlMappingWithRequet(path, POST, TEXT_HTML);
		assertEquals(restfulContext.getMappingUrl(), "addUserTiny.servicepage");
		restfulContext=urlRestfulManager.getUrlMappingWithRequet(path, PUT, TEXT_HTML);
		assertEquals(restfulContext.getMappingUrl(), "updateUserTiny.servicepage");
		restfulContext=urlRestfulManager.getUrlMappingWithRequet(path, DELETE, TEXT_HTML);
		assertEquals(restfulContext.getMappingUrl(), "deleteUserTiny.servicepage");
		//ACCEPT="text/json"
		restfulContext=urlRestfulManager.getUrlMappingWithRequet(path, GET, TEXT_JSON);
		assertEquals(restfulContext.getMappingUrl(), "queryUsersTiny.servicejson");
		
		path="/users/new/";
		restfulContext=urlRestfulManager.getUrlMappingWithRequet(path, GET, TEXT_HTML);
		assertEquals(restfulContext.getMappingUrl(), "crud/restful/operate.page");
		
		path="/users/edit/15";
		restfulContext=urlRestfulManager.getUrlMappingWithRequet(path, GET, TEXT_HTML);
		assertEquals(restfulContext.getMappingUrl(), "queryUserByIdTiny.servicepage");
		assertEquals("15", restfulContext.getVariableMap().get("id"));
		
		path="/users/12/Tuser";
		restfulContext=urlRestfulManager.getUrlMappingWithRequet(path, POST, TEXT_HTML);
		assertEquals(restfulContext.getMappingUrl(), "addUserTiny.servicepage");
		assertEquals("12", restfulContext.getVariableMap().get("id"));
		assertEquals("Tuser", restfulContext.getVariableMap().get("@beantype"));
		
		path="/users/12/classes/一年级";
		restfulContext=urlRestfulManager.getUrlMappingWithRequet(path, POST, TEXT_HTML);
		assertEquals(restfulContext.getMappingUrl(), "queryclasses.servicepage");
		assertEquals("12", restfulContext.getVariableMap().get("id"));
		assertEquals("一年级", restfulContext.getVariableMap().get("name"));
		
		//未找到匹配的
		path="/students/45";
		restfulContext= urlRestfulManager.getUrlMappingWithRequet(path, GET, TEXT_HTML);
		assertNull(restfulContext);
	}

}
