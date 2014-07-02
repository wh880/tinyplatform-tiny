package org.tinygroup.springutil.test.beancontainer;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.springutil.SpringBeanContainer;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class BeanContainerFactoryTest extends TestCase {
	public void testFactory() {
		BeanContainerFactory
				.setBeanContainer("org.tinygroup.springutil.SpringBeanContainer");
		BeanContainer<ApplicationContext> container = (BeanContainer<ApplicationContext>) BeanContainerFactory
				.getBeanContainer();
		assertNotNull(container);
		FileObject f = VFS.resolveURL(this.getClass().getClassLoader()
				.getResource("beancontainer.beans.xml"));
		List<FileObject> fl = new ArrayList<FileObject>();
		fl.add(f);
		ApplicationContext app = container.getSubBeanContainer(fl, this
				.getClass().getClassLoader());
		assertNotNull(app);
		assertEquals(1, container.getSubBeanContainers().size());

	}
	
	public void testFactoryGetContainer() {
		BeanContainerFactory
				.setBeanContainer("org.tinygroup.springutil.SpringBeanContainer");
		BeanContainer<ApplicationContext> container = (BeanContainer<ApplicationContext>) BeanContainerFactory
				.getBeanContainer();
		assertNotNull(container);
		FileObject f = VFS.resolveURL(this.getClass().getClassLoader()
				.getResource("beancontainer.beans.xml"));
		SpringBeanContainer spring = (SpringBeanContainer)container;
		List<FileObject> fl = new ArrayList<FileObject>();
		fl.add(f);
		spring.regSpringConfigXml(fl);
		spring.refresh();
		ContainerBean bean = spring.getBean("containerbean");
		assertNotNull(bean);

	}
}
