package org.tinygroup.weblayer.webcontext.session.model;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weblayer.webcontext.session.SessionManager;

public class SessionManagerFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SessionManagerFactory.class);

	public static SessionManager getSessionManager(String beanId,
			ClassLoader classLoader) {
		if(StringUtil.isBlank(beanId)){
			return DefaultSessionManager.getSingleton();
		}
		SessionManager manager = null;
		try {
			manager = BeanContainerFactory.getBeanContainer(classLoader)
					.getBean(beanId);
		} catch (Exception e) {
			LOGGER.logMessage(
					LogLevel.DEBUG,
					"在容器中找不到beanId:[{0}],SessionManager类型的bean,将创建默认的DefaultSessionManager");
		}
		if (manager == null) {
			manager = DefaultSessionManager.getSingleton();
		}
		return manager;
	}

}
