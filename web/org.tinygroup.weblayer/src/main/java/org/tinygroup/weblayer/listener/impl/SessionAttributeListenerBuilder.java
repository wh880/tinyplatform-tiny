package org.tinygroup.weblayer.listener.impl;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

public class SessionAttributeListenerBuilder extends AbstractListenerBuilder<HttpSessionAttributeListener> {

	public boolean isTypeMatch(Object object) {
		return HttpSessionListener.class.isInstance(object);
	}

	@Override
	protected HttpSessionAttributeListener replaceListener(
			HttpSessionAttributeListener listener) {
		return new DefaultSessionAttributeListener(listener);
	}

}
