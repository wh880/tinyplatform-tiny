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
package org.tinygroup.remoteconfig.web.test;

import java.util.ArrayList;
import java.util.Map;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.remoteconfig.web.service.test.TestService;
import org.tinygroup.remoteconfig.zk.manager.impl.ZKConfigClientImpl;
import org.tinygroup.tinyrunner.Runner;

public class GeneratorServiceTest extends TestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Runner.setRemoteConfigReadClient(new ZKConfigClientImpl());
		Runner.init("application.xml", new ArrayList<String>());
	}
	
	public void testApplicationPro() {
		Map<String ,String> propertyMap = ConfigurationUtil.getConfigurationManager().getConfiguration();
		assertEquals("名字1" ,propertyMap.get("name11"));
		assertEquals("名字2" ,propertyMap.get("name22"));
	}
	
	/**
	 * 验证application.xml里定义的配置文件
	 */
	public void testApplicationProFile() {
		Map<String ,String> propertyMap = ConfigurationUtil.getConfigurationManager().getConfiguration();
		assertEquals("名字3" ,propertyMap.get("name33"));
		propertyMap.put("a", "asda阿斯顿");
		assertEquals("asda阿斯顿" ,propertyMap.get("a"));
	}
	
	/**
	 * 验证spring中的property
	 */
	public void testSpringPro() {
		TestService testService = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean("testService");
		if (testService != null) {
			assertEquals("名字1" ,testService.getName11());
			assertEquals("名字2" ,testService.getName22());
			return;
		}
		fail();
	}
}
