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
package org.tinygroup.service;

import org.tinygroup.context.Context;
import org.tinygroup.service.exception.ServiceExecuteException;
import org.tinygroup.service.exception.ServiceNotExistException;
import org.tinygroup.service.registry.ServiceRegistry;
import org.tinygroup.service.registry.ServiceRegistryItem;

/**
 * 服务执行接口，每种服务容器都必须实现此接口
 * 
 * @author luoguo
 * 
 */
public interface ServiceProviderInterface {

	/**
	 * 设置服务注册表
	 * 
	 * @param serviceRegistry
	 */
	void setServiceRegistory(ServiceRegistry serviceRegistry);

	/**
	 * 返回服务注册表
	 */
	ServiceRegistry getServiceRegistory();

	/**
	 * 验证输入参数
	 * 
	 * @param service
	 * @return
	 * @throws ServiceNotExistException
	 */
	void validateInputParameter(Service service, Context context);

	/**
	 * 检查输出参数
	 * 
	 * @param service
	 */
	void validateOutputParameter(Service service, Context context);

	/**
	 * 执行服务
	 * 
	 * @param service
	 * @throws ServiceExecuteException
	 */
	void execute(Service service, Context context);

	/**
	 * 执行指定ID服务，并把服务结果放在环境当中
	 * 
	 * @param serviceId
	 * @param Context
	 */
	void execute(String serviceId, Context context);

	/**
	 * 执行指定ID指定版本服务，并把服务结果放在环境当中
	 * 
	 * @param serviceId
	 * @param version
	 * @param Context
	 */
	void execute(String serviceId, String version, Context context);

	Service getService(String serviceId);

	/**
	 * 注入参数
	 * 
	 * @param config
	 */
	<T> void setConfig(T config);

	ServiceRegistryItem getServiceRegistryItem(Service service);
}
