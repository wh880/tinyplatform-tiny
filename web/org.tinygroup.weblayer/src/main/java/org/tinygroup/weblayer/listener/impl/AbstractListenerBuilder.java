package org.tinygroup.weblayer.listener.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.order.Ordered;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weblayer.listener.ListenerInstanceBuilder;

/**
 * 
 * @author renhui
 * 
 * @param <INSTANCE>
 */
public abstract class AbstractListenerBuilder<INSTANCE> implements
		ListenerInstanceBuilder<INSTANCE> {

	protected List<INSTANCE> listeners = new ArrayList<INSTANCE>();

	private static Logger logger = LoggerFactory
			.getLogger(AbstractListenerBuilder.class);

	public void buildInstances(INSTANCE object) {
		INSTANCE listener = object;
		if (!imptOrdered(object)) {
			listener = replaceListener(object);
			logger.logMessage(LogLevel.DEBUG,
					"listener:[{0}] not implements Ordered will replace [{1}]",
					object.getClass().getSimpleName(), listener.getClass()
							.getSimpleName());
		}
		listeners.add(listener);
	}

	protected abstract INSTANCE replaceListener(INSTANCE listener);

	private boolean imptOrdered(Object object) {
		return object instanceof Ordered;
	}

	public List<INSTANCE> getInstances() {
		return listeners;
	}

}
