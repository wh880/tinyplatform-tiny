package org.tinygroup.weblayer.listener.impl;

import org.tinygroup.weblayer.listener.TinySessionListener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class DefaultSessionListener extends SimpleBasicTinyConfigAware
		implements TinySessionListener {

	private HttpSessionListener httpSessionListener;

	public DefaultSessionListener(HttpSessionListener httpSessionListener) {
		super();
		this.httpSessionListener = httpSessionListener;
	}

	public void sessionCreated(HttpSessionEvent se) {
		httpSessionListener.sessionCreated(se);
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		httpSessionListener.sessionDestroyed(se);
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

}
