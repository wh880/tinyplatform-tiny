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

package org.tinygroup.remoteconfig.service.wrapper;

import java.util.List;
import java.util.UUID;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;

public class RemoteConfigServiceImplWrapper implements org.tinygroup.remoteconfig.service.inter.RemoteConfigService {

	CEPCore cepcore;

	public CEPCore getCore() {
		return cepcore;
	}

	public void setCore(CEPCore cepcore) {
		this.cepcore = cepcore;
	}

	private Event getEvent(String serviceId,Context context) throws Exception{
		Event event = new Event();
		event.setEventId(UUID.randomUUID().toString());
		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setContext(context);
		serviceRequest.setServiceId(serviceId);
		event.setServiceRequest(serviceRequest);
		return event;
	}

	public void setClient(org.tinygroup.remoteconfig.manager.ConfigItemManager client) {
		String serviceId = "setClient";

		try{
			Context context = new ContextImpl();
			context.put("client" ,client);

			callService(serviceId,context);
		}catch(Exception e){
			throw new RuntimeException(String.format("服务[%s]发生异常",serviceId),e);
		}
	}

	public void add(org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem item) {
		String serviceId = "add";

		try{
			Context context = new ContextImpl();
			context.put("item" ,item);

			callService(serviceId,context);
		}catch(Exception e){
			throw new RuntimeException(String.format("服务[%s]发生异常",serviceId),e);
		}
	}

	public void set(java.lang.String oldId ,org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem item) {
		String serviceId = "set";

		try{
			Context context = new ContextImpl();
			context.put("oldId" ,oldId);
			context.put("item" ,item);

			callService(serviceId,context);
		}catch(Exception e){
			throw new RuntimeException(String.format("服务[%s]发生异常",serviceId),e);
		}
	}

	public java.lang.String get(org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem item) {
		String serviceId = "get";

		try{
			Context context = new ContextImpl();
			context.put("item" ,item);

			return callServiceAndCallBack(serviceId,context);
		}catch(Exception e){
			throw new RuntimeException(String.format("服务[%s]发生异常",serviceId),e);
		}
	}

	public java.util.Map getAll(org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem item) {
		String serviceId = "getAll";

		try{
			Context context = new ContextImpl();
			context.put("item" ,item);

			return callServiceAndCallBack(serviceId,context);
		}catch(Exception e){
			throw new RuntimeException(String.format("服务[%s]发生异常",serviceId),e);
		}
	}

	public void delete(org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem item) {
		String serviceId = "delete";

		try{
			Context context = new ContextImpl();
			context.put("item" ,item);

			callService(serviceId,context);
		}catch(Exception e){
			throw new RuntimeException(String.format("服务[%s]发生异常",serviceId),e);
		}
	}

	public void deletes(java.util.List<org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem> items) {
		String serviceId = "deletes";

		try{
			Context context = new ContextImpl();
			context.put("items" ,items);

			callService(serviceId,context);
		}catch(Exception e){
			throw new RuntimeException(String.format("服务[%s]发生异常",serviceId),e);
		}
	}

	public boolean isExit(org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem item) {
		String serviceId = "isExit";

		try{
			Context context = new ContextImpl();
			context.put("item" ,item);

			return callServiceAndCallBack(serviceId,context);
		}catch(Exception e){
			throw new RuntimeException(String.format("服务[%s]发生异常",serviceId),e);
		}
	}

	private void callService(String serviceId,Context context) throws Exception{
		Event event = getEvent(serviceId,context);
		cepcore.process(event);
	}

	private <T> T callServiceAndCallBack(String serviceId,Context context) throws Exception{
		Event event = getEvent(serviceId,context);
		cepcore.process(event);
		ServiceInfo info = cepcore.getServiceInfo(serviceId);
		List<Parameter> resultsParam = info.getResults();
		if (resultsParam==null||resultsParam.size() == 0) {
			return null;
	}
		return event.getServiceRequest().getContext()
			.get(resultsParam.get(0).getName());
	}

}