package org.tinygroup.cepcoreimpl.test.testcase.exception;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.tinygroup.cepcoreexceptioncode.CEPCoreImplExceptionCode;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.tinyrunner.Runner;

public class TestCEPConfigException extends TestCase {
	
	public void testConfigException() {
		try {
			Runner.init("application2.xml", new ArrayList<String>());
		} catch (BaseRuntimeException e) {
			assertEquals(e.getErrorCode().toString(),
					CEPCoreImplExceptionCode.CEP_CONFIG_EXCEPTION_CODE);
		}

	}
}
