package org.tinygroup.webservicetest;

import java.io.Serializable;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class ServiceABC implements Serializable{
	private static Logger logger = LoggerFactory.getLogger(ServiceABC.class);
	public void read() {
		logger.logMessage(LogLevel.INFO, "read");
	}

	public String write(int i, String s) {
		logger.logMessage(LogLevel.INFO, "write {} {}",i,s);
		return s+""+i;
	}

	public void write1(ServiceABC abc) {
		logger.logMessage(LogLevel.INFO, "write1");
	}
}
