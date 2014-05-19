package org.tinygroup.springutil.test.beancontainer;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.springutil.SpringBeanContainer;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class BeanContainerTest extends TestCase {

	public void testContainer() {
		SpringBeanContainer sbc = new SpringBeanContainer();
		FileObject f = VFS.resolveURL(this.getClass().getClassLoader().getResource("beancontainer.beans.xml"));
		List<FileObject> fl = new ArrayList<FileObject>();
		fl.add(f);
		sbc.regSpringConfigXml(fl);
		sbc.refresh();
		ContainerBean bean = (ContainerBean) sbc.getBean("containerbean");
		assertNotNull(bean);
	}

}
