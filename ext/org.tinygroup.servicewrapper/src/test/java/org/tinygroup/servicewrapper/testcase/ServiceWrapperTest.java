package org.tinygroup.servicewrapper.testcase;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.chainsaw.Main;
import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.servicewrapper.GeneratorServiceIn;
import org.tinygroup.servicewrapper.ServiceOrg;
import org.tinygroup.servicewrapper.ServiceUser;
import org.tinygroup.tinyrunner.Runner;

public class ServiceWrapperTest extends TestCase{
	public void testInterceptor(){
		Runner.init("application.xml", new ArrayList<String>());
    	BeanContainer container = BeanContainerFactory.getBeanContainer(Main.class.getClassLoader());
    	GeneratorServiceIn bean = (GeneratorServiceIn)container.getBean("busiServiceProxy");
        ServiceUser user = new ServiceUser();
        user.setName("username");
        user.setAge(11);
        ServiceOrg org = new ServiceOrg();
        org.setName("hundsun");
        ServiceUser serviceUser= bean.userObject(user,org);
        assertEquals("changerName", serviceUser.getName());
        
        //xml
        List<ServiceUser> users = new ArrayList<ServiceUser>();
        ServiceUser xmluser = new ServiceUser();
        xmluser.setAge(100);
        xmluser.setName("lilei");
        xmluser.setMale(true);
        users.add(xmluser);
        List<ServiceUser> users2 = bean.userList(user, users);
        assertEquals(users2.get(0).getAge(), 100);
	}
}
