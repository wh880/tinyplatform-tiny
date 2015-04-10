package org.tinygroup.weblayer.configmanager;

import org.tinygroup.beancontainer.BeanContainerFactory;

/**
 * TinyListenerConfigManager全局单实例
 * 
 * @author renhui
 * 
 */
public class TinyListenerConfigManagerHolder {

	private static TinyListenerConfigManager configManager;

	private TinyListenerConfigManagerHolder() {

	}

	public static TinyListenerConfigManager getInstance() {
		if (configManager == null) {
			configManager = BeanContainerFactory.getBeanContainer(
					TinyListenerConfigManagerHolder.class.getClassLoader())
					.getBean("tinyListenerConfigManager");
		}
		if (configManager == null) {
			configManager = new TinyListenerConfigManager();
		}
		return configManager;
	}
}
