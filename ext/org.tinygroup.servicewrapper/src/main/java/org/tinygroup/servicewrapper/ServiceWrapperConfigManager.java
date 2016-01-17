package org.tinygroup.servicewrapper;

import java.lang.reflect.Method;

import org.tinygroup.servicewrapper.config.MethodConfig;
import org.tinygroup.servicewrapper.config.MethodConfigs;

/**
 * aop缓存配置管理对象
 * @author renhui
 *
 */
public interface ServiceWrapperConfigManager {

	String XSTEAM_PACKAGE_NAME = "servicewrapper";

	void addServiceWrappers(MethodConfigs serviceWrappers);
	
	void removeServiceWrappers(MethodConfigs serviceWrappers);
		
	/**
	 * 根据方法获取对应的serviceId
	 * @param method
	 * @return
	 */
	String getServiceIdWithMethod(Method method);
	
	
	void putServiceWrapper(MethodConfig serviceWrapper);
}
