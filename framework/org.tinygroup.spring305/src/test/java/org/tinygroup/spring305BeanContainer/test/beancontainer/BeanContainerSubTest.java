package org.tinygroup.spring305BeanContainer.test.beancontainer;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.tinygroup.spring305BeanContainer.SpringBeanContainer;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class BeanContainerSubTest extends TestCase {

	public void testSubList() {
		ApplicationContext app = getSubContainer();
		assertNotNull(app);
	}

	public void testSubBean() {
		ApplicationContext app = getSubContainer();
		ContainerBean bean = (ContainerBean) app.getBean("containerbean");
		assertNotNull(bean);
	}

	private ApplicationContext getSubContainer() {
		SpringBeanContainer c = new SpringBeanContainer();
		FileObject f = VFS.resolveURL(this.getClass().getClassLoader().getResource("beancontainer.beans.xml"));
		List<FileObject> fl = new ArrayList<FileObject>();
		fl.add(f);
		ApplicationContext app = c.getSubBeanContainer(fl, this.getClass()
				.getClassLoader());
		assertEquals(1, c.getSubBeanContainers().size());
		return app;
	}
}
