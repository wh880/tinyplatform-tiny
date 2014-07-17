package org.tinygroup.springutil.test.beancontainer;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.springutil.SpringBeanContainer;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class BeanContainerSubTest extends TestCase {

	public void testSubList() {
		BeanContainer app = getSubContainer();
		assertNotNull(app);
	}

	public void testSubBean() {
		BeanContainer app = getSubContainer();
		ContainerBean bean = (ContainerBean) app.getBean("containerbean");
		assertNotNull(bean);
	}

	private BeanContainer getSubContainer() {
		SpringBeanContainer c = new SpringBeanContainer();
		FileObject f = VFS.resolveURL(this.getClass().getClassLoader().getResource("beancontainer.beans.xml"));
		List<FileObject> fl = new ArrayList<FileObject>();
		fl.add(f);
		BeanContainer app = c.getSubBeanContainer(fl, this.getClass()
				.getClassLoader());
		assertEquals(1, c.getSubBeanContainers().size());
		return app;
	}
}
