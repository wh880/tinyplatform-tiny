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
package org.tinygroup.cepcoreimpl.test;

import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;

import java.util.ArrayList;
import java.util.List;

public class ServiceInfoForTest implements ServiceInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6510299323984908078L;
	private String serviceId;
	private List<Parameter> in =new ArrayList<Parameter>(); 
	private List<Parameter> out =new ArrayList<Parameter>(); 
	
	public int compareTo(ServiceInfo o) {
		return 0;
	}

	public String getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<Parameter> getParameters() {
		return in;
	}

	public List<Parameter> getResults() {
		return out;
	}

	public String getCategory() {
		return null;
	}
	

}
