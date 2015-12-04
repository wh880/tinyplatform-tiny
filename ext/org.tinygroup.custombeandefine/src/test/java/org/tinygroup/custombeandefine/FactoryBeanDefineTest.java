package org.tinygroup.custombeandefine;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tinygroup.custombeandefine.service.SayHelloService;

public class FactoryBeanDefineTest  extends TestCase{

	private ClassPathXmlApplicationContext application;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		application=new ClassPathXmlApplicationContext("test.beans.xml");
		application.refresh();
	}

	public void testFactoryBean(){
		String[] beanNames=application.getBeanNamesForType(SayHelloService.class);
		SayHelloService sayHelloService=(SayHelloService) application.getBean(beanNames[0]);
		sayHelloService.sayHello();
		assertEquals("invoke method", VariableHolder.getInstance().getVariable());
	}
	
	
}
