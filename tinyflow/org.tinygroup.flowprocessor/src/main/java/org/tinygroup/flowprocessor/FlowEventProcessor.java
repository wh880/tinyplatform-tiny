/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.flowprocessor;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.impl.AbstractEventProcessor;
import org.tinygroup.context.Context;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.flow.FlowExecutor;
import org.tinygroup.flow.config.Flow;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FlowEventProcessor extends AbstractEventProcessor {
	/**
	 * 流程执行器，用于执行具体的Service
	 */
	private FlowExecutor executor;

	private static Logger logger = LoggerFactory
			.getLogger(FlowEventProcessor.class);

	public void process(Event event) {
		Flow flow = null;
		String nodeId = null;
		String serviceId = event.getServiceRequest().getServiceId();
		String flowId = serviceId;
		String[] str = serviceId.split(":");
		if (str.length > 1) {
			nodeId = str[str.length - 1];
			flowId = serviceId.substring(0,
					serviceId.length() - nodeId.length() - 1);
		}
		flow = executor.getFlow(flowId);
		if (flow != null) {
			executor.execute(flowId, nodeId, (Context) event
					.getServiceRequest().getContext());
		} else {
			logger.logMessage(LogLevel.ERROR, "[Flow:{0}]不存在或无合适的Flow流程执行器",
					flowId);
			throw new RuntimeException("[Flow:" + flowId + "]不存在或无合适的Flow流程执行器");
		}

	}

	public void setCepCore(CEPCore cepCore) {
	}

	public void setExecutor(FlowExecutor executor) {
		this.executor = executor;
	}

	public List<ServiceInfo> getServiceInfos() {
		List<ServiceInfo> list = new ArrayList<ServiceInfo>();
		for (Flow f : executor.getFlowIdMap().values()) {
			list.add(new FlowServiceInfo(f));
		}
		return list;
	}

	public int getWeight() {
		return 0;
	}

	public List<String> getRegex() {
		return null;
	}

	public boolean isRead() {
		return !executor.isChange();
	}

	public void setRead(boolean read) {
		executor.setChange(!read);
	}

}
