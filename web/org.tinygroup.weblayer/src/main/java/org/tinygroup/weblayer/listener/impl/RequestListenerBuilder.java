package org.tinygroup.weblayer.listener.impl;

import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionListener;

public class RequestListenerBuilder extends AbstractListenerBuilder<ServletRequestListener> {

	public boolean isTypeMatch(Object object) {
		return HttpSessionListener.class.isInstance(object);
	}

	@Override
	protected ServletRequestListener replaceListener(
			ServletRequestListener listener) {
		return new DefaultRequestListener(listener);
	}

}
