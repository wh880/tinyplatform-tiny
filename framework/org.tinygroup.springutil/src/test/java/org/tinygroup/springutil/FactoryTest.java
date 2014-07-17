/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.springutil;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.springutil.config.User;

public class FactoryTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		BeanContainerFactory.setBeanContainer(SpringBeanContainer.class
				.getName());
		((SpringBeanContainer) (BeanContainerFactory
				.getBeanContainer(FactoryTest.class.getClassLoader())))
				.regSpringConfigXml("/Test.beans.xml");
		((SpringBeanContainer) (BeanContainerFactory
				.getBeanContainer(FactoryTest.class.getClassLoader())))
				.refresh();

	}

	public void testInitComparePrototype1() {
		User user = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader()).getBean("user2");
		User user2 = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader()).getBean("user2");
		assertEquals(false, user == user2);
		assertEquals(false, user.getCatMap() == user2.getCat());
	}

}
