package org.tinygroup.weblayer.applicationprocessor;

import org.tinygroup.application.AbstractApplicationProcessor;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weblayer.webcontext.session.Session;
import org.tinygroup.weblayer.webcontext.session.SessionConfig;
import org.tinygroup.weblayer.webcontext.session.SessionConfiguration;
import org.tinygroup.weblayer.webcontext.session.SessionManager;
import org.tinygroup.weblayer.webcontext.session.model.SessionManagerFactory;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 监控session的应用处理器
 * 
 * @author renhui
 *
 */
public class SessionMonitorApplicationProcessor extends
		AbstractApplicationProcessor {

	/**
	 * The background thread.
	 */
	private Thread thread = null;

	private boolean threadDone;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SessionBackgroundProcessor.class);

	public void start() {
		if (thread != null) {
			return;
		}
		threadDone = false;
		String threadName = "SessionBackgroundProcessor";
		thread = new Thread(new SessionBackgroundProcessor(), threadName);
		thread.setDaemon(true);
		thread.start();
	}

	public void stop() {
		if (thread == null) {
			return;
		}
		threadDone = true;
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
		thread = null;
	}

	public String getApplicationNodePath() {
		return null;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {

	}

	public XmlNode getComponentConfig() {
		return null;
	}

	public XmlNode getApplicationConfig() {
		return null;
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

	class SessionBackgroundProcessor implements Runnable {

		private static final String SESSION_CONFIGURATION_BEAN_NAME = "sessionConfiguration";

		public SessionBackgroundProcessor() {
			super();
		}

		public void run() {
			SessionConfiguration sessionConfiguration = BeanContainerFactory
					.getBeanContainer(getClass().getClassLoader()).getBean(
							SESSION_CONFIGURATION_BEAN_NAME);
			SessionConfig sessionConfig = sessionConfiguration
					.getSessionConfig();
			SessionManager sessionManager = SessionManagerFactory
					.getSessionManager(sessionConfig.getSessionManagerBeanId(),
							getClass().getClassLoader());
			int backgroundProcessorDelay = sessionConfig
					.getBackgroundProcessorDelay();
			while (!threadDone) {
				try {
					Thread.sleep(backgroundProcessorDelay * 1000L);
				} catch (InterruptedException e) {
				}
				try {
					if (!threadDone) {
						processExpires(sessionManager);
					}
				} catch (Exception e) {
					LOGGER.errorMessage("监控session失效操作出现异常", e);
				}
			}
		}

		public void processExpires(SessionManager sessionManager) {
			Session[] sessions = sessionManager.queryAllSessions();
			long timeNow = System.currentTimeMillis();
			int expireHere = 0;
			LOGGER.logMessage(LogLevel.DEBUG,
					"Start expire sessions at {0} sessioncount:{1}", timeNow,
					sessions.length);
			for (int i = 0; i < sessions.length; i++) {
				if (sessions[i] != null && sessions[i].isExpired()) {
					sessions[i].invalidate();
					expireHere++;
				}
			}
			long timeEnd = System.currentTimeMillis();
			LOGGER.logMessage(
					LogLevel.DEBUG,
					"End expire sessions  processingTime:{0} expired sessions: {1}",
					timeEnd - timeNow, expireHere);
		}
	}

}
