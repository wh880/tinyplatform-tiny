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
package org.tinygroup.servicebasicservice;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.service.registry.ServiceRegistry;
import org.tinygroup.service.registry.ServiceRegistryItem;

public class BasicService {
	private ServiceRegistry serviceRegistry;

	public int getServiceCount() {
		return serviceRegistry.getServiceRegistryItems().size();
	}

	public List<ServiceRegistryItem> getServiceRegistryItems() {
		List<ServiceRegistryItem> list = new ArrayList<ServiceRegistryItem>();
		for (ServiceRegistryItem item : serviceRegistry
				.getServiceRegistryItems()) {
			list.add(item);
		}
		return list;
	}

	public ServiceRegistryItem getServiceRegistryItem(String id) {
		return serviceRegistry.getServiceRegistryItem(id);
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

}
