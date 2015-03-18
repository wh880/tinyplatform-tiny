package org.tinygroup.weblayer.listener.impl;

import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionListener;

public class SessionBindingListenerBuilder extends AbstractListenerBuilder<HttpSessionBindingListener> {

	public boolean isTypeMatch(Object object) {
		return HttpSessionListener.class.isInstance(object);
	}

	@Override
	protected HttpSessionBindingListener replaceListener(
			HttpSessionBindingListener listener) {
		return new DefaultSessionBindingListener(listener);
	}

}
