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

	public void addServiceWrappers(MethodConfigs serviceWrappers);
	
	public void removeServiceWrappers(MethodConfigs serviceWrappers);
		
	/**
	 * 根据方法获取对应的serviceId
	 * @param method
	 * @return
	 */
	public String getServiceIdWithMethod(Method method);
	
	
	public void putServiceWrapper(MethodConfig serviceWrapper);
}
