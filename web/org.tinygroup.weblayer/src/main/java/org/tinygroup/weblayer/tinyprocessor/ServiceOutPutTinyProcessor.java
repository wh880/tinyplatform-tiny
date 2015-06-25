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
package org.tinygroup.weblayer.tinyprocessor;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.context.Context;
import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.convert.objectxml.xstream.ObjectToXml;
import org.tinygroup.event.Event;
import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.exception.Result;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author renhui
 * 
 */
public class ServiceOutPutTinyProcessor extends AbstractTinyProcessor {
	private CEPCore core;
	private ObjectToXml<Object> objectToXml = new ObjectToXml<Object>();
	private ObjectToJson<Object> objectToJson = new ObjectToJson<Object>(
			SerializerFeature.DisableCircularReferenceDetect);

	public CEPCore getCore() {
		return core;
	}

	public void setCore(CEPCore core) {
		this.core = core;
	}

	private Object callService(String serviceId, Context context) {
		Event event = new Event();
		ServiceRequest sq = new ServiceRequest();
		sq.setServiceId(serviceId);
		sq.setContext(context);
		event.setServiceRequest(sq);
		core.process(event);

		ServiceInfo info = core.getServiceInfo(serviceId);
		List<Parameter> resultsParam = info.getResults();
		if (resultsParam == null || resultsParam.size() == 0) {
			return null;
		}
		return event.getServiceRequest().getContext()
				.get(resultsParam.get(0).getName());
	}

	public void reallyProcess(String urlString, WebContext context)
			throws ServletException, IOException {
		Result result = new Result();
		try {
			int lastSplash = urlString.lastIndexOf('/');
			int lastDot = urlString.lastIndexOf('.');
			String serviceId = urlString.substring(lastSplash + 1, lastDot);
			Object objectResult = callService(serviceId, context);
			result.setResultObj(objectResult);
			result.setSuccess(true);
		} catch (Throwable throwable) {
			result.setSuccess(false);
			result.setErrorContext(BaseRuntimeException
					.getErrorContext(throwable));
		}
		if (urlString.endsWith(".sxml")) {// 返回xml
			context.getResponse().getWriter()
					.write(objectToXml.convert(result));
		} else if (urlString.endsWith(".sjson")) {// 返回json
			context.getResponse().getWriter()
					.write(objectToJson.convert(result));
		}
	}

	@Override
	protected void customInit() throws ServletException {

	}
}
