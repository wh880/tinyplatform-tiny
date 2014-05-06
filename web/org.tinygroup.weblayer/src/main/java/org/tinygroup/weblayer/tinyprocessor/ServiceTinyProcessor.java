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
package org.tinygroup.weblayer.tinyprocessor;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.context.Context;
import org.tinygroup.convert.objectjson.jackson.ObjectToJson;
import org.tinygroup.convert.objectxml.xstream.ObjectToXml;
import org.tinygroup.event.Event;
import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.service.ServiceMappingManager;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;

public class ServiceTinyProcessor extends AbstractTinyProcessor {
	ServiceMappingManager manager;
	ObjectToXml<Object> objectToXml = new ObjectToXml<Object>();
	ObjectToJson<Object> objectToJson = new ObjectToJson<Object>(
			JsonSerialize.Inclusion.NON_NULL);

	public ServiceMappingManager getManager() {
		return manager;
	}

	public void setManager(ServiceMappingManager manager) {
		this.manager = manager;
	}

	private Object callService(String serviceId, Context context) {
		CEPCore core = SpringUtil.getBean(CEPCore.CEP_CORE_BEAN);
		Event event = new Event();
		ServiceRequest sq = new ServiceRequest();
		sq.setServiceId(serviceId);
		sq.setContext(context);
		event.setServiceRequest(sq);
		core.process(event);

		ServiceInfo info = core.getServiceInfo(serviceId);
		List<Parameter> resultsParam = info.getResults();
		if (resultsParam.size() == 0) {
			return null;
		}
		return event.getServiceRequest().getContext()
				.get(resultsParam.get(0).getName());
	}


	public void reallyProcess(String urlString, WebContext context) {
		int lastSplash = urlString.lastIndexOf('/');
		int lastDot = urlString.lastIndexOf('.');
		try {
			String serviceId = urlString.substring(lastSplash + 1, lastDot);
			Object result = callService(serviceId, context);
			if (urlString.endsWith("servicexml") && result != null) {// 返回xml
				context.getResponse().getWriter()
						.write(objectToXml.convert(result));
			} else if (urlString.endsWith(".servicejson") && result != null) {// 返回json
				context.getResponse().getWriter()
						.write(objectToJson.convert(result));
			} else if (urlString.endsWith(".servicepage")) {// 返回页面
				String path = manager.getUrl(serviceId);
				checkPath(serviceId, path);
				context.getRequest().getRequestDispatcher(path)
						.forward(context.getRequest(), context.getResponse());
			} else if (urlString.endsWith(".servicepagelet")) {// 返回页面片断
				String path = manager.getUrl(serviceId);
				checkPath(serviceId, path);
				if (path.endsWith(".page")) {
					path = path + "let";
				}
				context.getRequest().getRequestDispatcher(path)
						.forward(context.getRequest(), context.getResponse());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void checkPath(String serviceId, String path) {
		if (path == null) {
			throw new RuntimeException(serviceId + "对应的展现视图不存在！");
		}
	}
}
