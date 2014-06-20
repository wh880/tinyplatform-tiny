/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.cepcorepc.test.service.testcase;

import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.cepcore.exception.RequestNotFoundException;
import org.tinygroup.cepcorepc.test.aop.util.AopTestUtil;
import org.tinygroup.cepcorepc.test.aop.util.ToolUtil;
import org.tinygroup.event.ServiceInfo;

public class ServiceGetTest extends TestCase {
	public void testServiceGet() {
		ServiceInfo aoptest = AopTestUtil.getService("aoptest");
		ServiceInfo aoptest1 = getServiceInfo("aoptest");
		assertService(aoptest, aoptest1);
		
		ServiceInfo exceptionService = AopTestUtil.getService("exceptionService");
		ServiceInfo exceptionService1 = getServiceInfo("exceptionService");
		assertService(exceptionService, exceptionService1);
	}
	
	public void testServiceGetException() {
		try {
			AopTestUtil.getService("noservice");
			assertTrue(false);
		} catch (RequestNotFoundException e) {
			assertTrue(true);
		}
		
		
	}
	private void assertService(ServiceInfo target,ServiceInfo source){
		assertEquals(source.getServiceId(),target.getServiceId());
		assertEquals(source.getParameters().size(), target.getParameters().size());
		assertEquals(source.getResults().size(), target.getResults().size());
	}
	
	private ServiceInfo getServiceInfo(String id){
		List<ServiceInfo> services = ToolUtil.getEventProcessor1().getServiceInfos();
		for(ServiceInfo service:services){
			if(service.getServiceId().equals(id)){
				return service;
			}
		}
		throw new RuntimeException("未找到服务:"+id);
	}
}