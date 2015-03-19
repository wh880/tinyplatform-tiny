package org.tinygroup.weblayer.listener.impl;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionListener;

public class SessionActivationListenerBuilder extends AbstractListenerBuilder<HttpSessionActivationListener> {

	public boolean isTypeMatch(Object object) {
		return HttpSessionListener.class.isInstance(object);
	}

	@Override
	protected HttpSessionActivationListener replaceListener(
			HttpSessionActivationListener listener) {
		return new DefaultSessionActivationListener(listener);
	}

}
