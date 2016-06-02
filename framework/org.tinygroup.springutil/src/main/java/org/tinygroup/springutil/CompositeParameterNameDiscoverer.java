package org.tinygroup.springutil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.ParameterNameDiscoverer;
import org.tinygroup.assembly.AssemblyService;
import org.tinygroup.assembly.DefaultAssemblyService;
import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 解析方法参数名称的复合对象
 * 
 * @author renhui
 *
 */
public class CompositeParameterNameDiscoverer extends ApplicationObjectSupport
		implements ParameterNameDiscoverer, InitializingBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CompositeParameterNameDiscoverer.class);

	private List<ParameterNameDiscoverer> discoverers = new ArrayList<ParameterNameDiscoverer>();

	private AssemblyService<ParameterNameDiscoverer> assemblyService = new DefaultAssemblyService<ParameterNameDiscoverer>();

	public void setAssemblyService(
			AssemblyService<ParameterNameDiscoverer> assemblyService) {
		this.assemblyService = assemblyService;
	}

	public String[] getParameterNames(Method method) {
		for (ParameterNameDiscoverer parameterNameDiscoverer : discoverers) {
			String[] parameterNames = parameterNameDiscoverer
					.getParameterNames(method);
			LOGGER.logMessage(
					LogLevel.DEBUG,
					"method:{0},ParameterNameDiscoverer:{1},parameterNames:{2}",
					method, parameterNameDiscoverer, parameterNames);
			if (!ArrayUtil.isEmptyArray(parameterNames)) {
				return parameterNames;
			}
		}
		return null;
	}

	public String[] getParameterNames(Constructor ctor) {
		for (ParameterNameDiscoverer parameterNameDiscoverer : discoverers) {
			String[] parameterNames = parameterNameDiscoverer
					.getParameterNames(ctor);
			if (!ArrayUtil.isEmptyArray(parameterNames)) {
				return parameterNames;
			}
		}
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		if (CollectionUtil.isEmpty(discoverers)) {
			List<ParameterNameDiscoverer> exclusions = new ArrayList<ParameterNameDiscoverer>();
			exclusions.add(this);
			assemblyService.setApplicationContext(getApplicationContext());
			assemblyService.setExclusions(exclusions);
			discoverers = assemblyService
					.findParticipants(ParameterNameDiscoverer.class);
		}

	}

}
