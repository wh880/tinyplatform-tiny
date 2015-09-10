package org.tinygroup.cepcoreimpl.test.testcase;

import java.util.ArrayList;

import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinyrunner.Runner;

public class ShowExceptionLog {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShowExceptionLog.class); 
	public static void main(String[] args) {
		try {
			Runner.init("application2.xml", new ArrayList<String>());
		} catch (BaseRuntimeException e) {
			LOGGER.errorMessage("异常", e);
			
		}
	}
}
