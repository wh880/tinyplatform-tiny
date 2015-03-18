package org.tinygroup.weblayer.listener.impl;

import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.http.HttpSessionListener;

public class RequestAttributeListenerBuilder extends AbstractListenerBuilder<ServletRequestAttributeListener> {

	public boolean isTypeMatch(Object object) {
		return HttpSessionListener.class.isInstance(object);
	}

	@Override
	protected ServletRequestAttributeListener replaceListener(
			ServletRequestAttributeListener listener) {
		return new DefaultRequestAttributeListener(listener);
	}

}
