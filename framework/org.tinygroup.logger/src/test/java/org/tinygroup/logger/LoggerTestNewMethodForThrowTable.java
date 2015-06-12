package org.tinygroup.logger;

import junit.framework.TestCase;

public class LoggerTestNewMethodForThrowTable extends TestCase {

	static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
	
	public void testExceptionINFO(){
		try {
			generateException();
		} catch (Exception e) {
			logger.logMessage(LogLevel.INFO, "-------INFO-------",e);
		}
		
	}
	
	public void testExceptionDEBUG(){
		try {
			generateException();
		} catch (Exception e) {
			logger.logMessage(LogLevel.DEBUG, "-------DEBUG-------",e);
		}
		
	}
	
	public void testExceptionWARN(){
		try {
			generateException();
		} catch (Exception e) {
			logger.logMessage(LogLevel.WARN, "-------WARN-------",e);
		}
		
	}
	
	public void testExceptionERROR(){
		try {
			generateException();
		} catch (Exception e) {
			logger.logMessage(LogLevel.ERROR, "-------ERROR-------",e);
		}
		
	}
	
	
	private void generateException(){
		String a = null;
		a.indexOf(5);
	}
}
