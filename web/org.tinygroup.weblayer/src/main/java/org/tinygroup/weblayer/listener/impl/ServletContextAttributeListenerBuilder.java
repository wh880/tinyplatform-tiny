package org.tinygroup.weblayer.listener.impl;

import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;

/**
 * 创建ServletContextAttributeListener
 * @author renhui
 *
 */
public class ServletContextAttributeListenerBuilder extends
		AbstractListenerBuilder<ServletContextAttributeListener> {

	public boolean isTypeMatch(Object object) {
		return ServletContextListener.class.isInstance(object);
	}

	@Override
	protected DefaultServletContextAttributeListener replaceListener(
			ServletContextAttributeListener contextListener) {
		return new DefaultServletContextAttributeListener(contextListener);
	}

}
