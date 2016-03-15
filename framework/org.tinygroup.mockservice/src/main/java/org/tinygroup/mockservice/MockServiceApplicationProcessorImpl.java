package org.tinygroup.mockservice;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.xmlparser.node.XmlNode;

public class MockServiceApplicationProcessorImpl implements
		ApplicationProcessor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MockServiceApplicationProcessorImpl.class);
	private CEPCore cepcore;
	private MockServiceEventProcessorImpl processor;

	public String getApplicationNodePath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getComponentConfigPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		// TODO Auto-generated method stub

	}

	public XmlNode getComponentConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlNode getApplicationConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void start() {
		LOGGER.logMessage(LogLevel.DEBUG, "启动MockServiceProcessor");
		cepcore.registerEventProcessor(processor);
		LOGGER.logMessage(LogLevel.DEBUG, "启动MockServiceProcessor完成");

	}

	public void init() {
//		LOGGER.logMessage(LogLevel.DEBUG, "初始化MockServiceProcessor");
//		processor = new MockServiceEventProcessorImpl();
//		processor.setManager(manager);
//		LOGGER.logMessage(LogLevel.DEBUG, "初始化MockServiceProcessor完成");
	}

	public void stop() {
		LOGGER.logMessage(LogLevel.DEBUG, "停止MockServiceProcessor");
		cepcore.unregisterEventProcessor(processor);
		LOGGER.logMessage(LogLevel.DEBUG, "停止MockServiceProcessor完成");
	}

	public void setApplication(Application application) {
		// TODO Auto-generated method stub

	}

	

	public CEPCore getCepcore() {
		return cepcore;
	}

	public void setCepcore(CEPCore cepcore) {
		this.cepcore = cepcore;
	}

	public MockServiceEventProcessorImpl getProcessor() {
		return processor;
	}

	public void setProcessor(MockServiceEventProcessorImpl processor) {
		this.processor = processor;
	}

}
