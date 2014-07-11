package org.tinygroup.webservicetest;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class ServiceABC {
	private static Logger logger = LoggerFactory.getLogger(ServiceABC.class);
	public void read() {
		logger.logMessage(LogLevel.INFO, "read");
	}

	public void write(int i, String s) {
		logger.logMessage(LogLevel.INFO, "write {} {}",i,s);
	}

	public void write1(ServiceABC abc) {
		logger.logMessage(LogLevel.INFO, "write1");
	}
}
