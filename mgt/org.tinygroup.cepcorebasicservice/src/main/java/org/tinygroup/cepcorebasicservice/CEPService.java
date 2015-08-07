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
package org.tinygroup.cepcorebasicservice;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.event.ServiceInfo;

public class CEPService {

	private CEPCore core;

	public CEPCore getCore() {
		return core;
	}

	public void setCore(CEPCore core) {
		this.core = core;
	}
	
	public int getServiceCount(){
		return core.getServiceInfos().size();
	}
	
	public List<ServiceInfo> getServiceInfos(){
		return core.getServiceInfos();
	}
	
	public ServiceInfo getServiceInfo(String serviceId){
		return core.getServiceInfo(serviceId);
	}
	
	public List<EventProcessor> getRemoteEventProcessors(){
		List<EventProcessor> list = new ArrayList<EventProcessor>();
		for(EventProcessor e:getCore().getEventProcessors()){
			if(EventProcessor.TYPE_REMOTE==e.getType()){
				list.add(e);
			}
		}
		return list;
	}
	
	public List<EventProcessor> getLocalEventProcessors(){
		List<EventProcessor> list = new ArrayList<EventProcessor>();
		for(EventProcessor e:getCore().getEventProcessors()){
			if(EventProcessor.TYPE_LOCAL==e.getType()){
				list.add(e);
			}
		}
		return list;
	}
	
	public List<EventProcessor> getEventProcessors(){
		List<EventProcessor> list = new ArrayList<EventProcessor>();
		for(EventProcessor e:getCore().getEventProcessors()){
			
				list.add(e);
		}
		return list;
	}
	
	
	public EventProcessor getEventProcessor(String eventProcessorId){
		if(StringUtil.isBlank(eventProcessorId)){
			throw new RuntimeException("EventProcessorId不可为空");
		}
		List<EventProcessor> list = getEventProcessors();
		for(EventProcessor e:list){
			if (eventProcessorId.equals(e.getId())) {
				return e;
			}
		}
		throw new RuntimeException("不存在EventProcessor:"+eventProcessorId);
	}
	
}
