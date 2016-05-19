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
package org.tinygroup.metadata.config.service;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.tinygroup.metadata.config.BaseObject;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("service-method")
public class ServiceMethod extends BaseObject{
	@XStreamAsAttribute
	@XStreamAlias("service-id")
	private String serviceId;
	@XStreamAsAttribute
	@XStreamAlias("method-name")
	private String methodName;
	@XStreamAsAttribute
	private String alias;
	@XStreamAsAttribute
	private String version;
	@XStreamAlias("service-parameters")
	private List<ServiceParameter> serviceParameters;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<ServiceParameter> getServiceParameters() {
		if (serviceParameters == null)
			serviceParameters = new ArrayList<ServiceParameter>();
		return serviceParameters;
	}

	public void setServiceParameters(List<ServiceParameter> serviceParameters) {
		this.serviceParameters = serviceParameters;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
