package org.tinygroup.bundle.test.activator;

import org.tinygroup.bundle.BundleActivator;
import org.tinygroup.bundle.BundleContext;
import org.tinygroup.bundle.BundleException;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class Activator1 implements BundleActivator {
	
	private Logger logger = LoggerFactory.getLogger(Activator1.class);
	
	public void start(BundleContext bundleContext) throws BundleException {
		logger.logMessage(LogLevel.INFO, "================start================");
	}

	public void stop(BundleContext bundleContext) throws BundleException {
		logger.logMessage(LogLevel.INFO, "================stop================");
	}

}
