package org.tinygroup.flow.test.newtestcase;
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


import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.flow.FlowExecutor;
import org.tinygroup.tinyrunner.Runner;

/**
 * 
 * @description：Tiny环境初始化，包括加载spring,文件扫描器，文件处理器等等
 * @author: qiuqn
 * @version: 2016年4月27日 上午9:50:22
 */
public abstract class AbstractFlowTestcase extends TestCase {

	protected FlowExecutor flowExecutor;
	protected FlowExecutor pageFlowExecutor;

	void init() {
		Runner.init("application.xml", null);
	}

	protected void setUp() throws Exception {
		init();
		flowExecutor = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader()).getBean(
				FlowExecutor.FLOW_BEAN);
		pageFlowExecutor = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader()).getBean(
				FlowExecutor.PAGE_FLOW_BEAN);
	}

}
