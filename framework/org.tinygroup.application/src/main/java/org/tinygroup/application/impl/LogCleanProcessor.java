package org.tinygroup.application.impl;

import org.tinygroup.application.AbstractApplicationProcessor;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.xmlparser.node.XmlNode;

public class LogCleanProcessor extends AbstractApplicationProcessor{

	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		LoggerFactory.clearAllLoggers();
	}

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

}
