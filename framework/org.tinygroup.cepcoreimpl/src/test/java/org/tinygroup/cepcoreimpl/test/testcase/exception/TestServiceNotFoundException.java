package org.tinygroup.cepcoreimpl.test.testcase.exception;

import org.tinygroup.cepcoreexceptioncode.CEPCoreExceptionCode;
import org.tinygroup.cepcoreimpl.test.testcase.CEPCoreBaseTestCase;
import org.tinygroup.exception.BaseRuntimeException;

public class TestServiceNotFoundException extends CEPCoreBaseTestCase {
	public void setUp() {
		super.setUp();
	}

	public void testServiceNotFoundExcetpion() {
		try {
			getCore().getServiceInfo("aaaaa");
		} catch (BaseRuntimeException e) {
			assertEquals(e.getErrorCode().toString(),
					CEPCoreExceptionCode.SERVICE_NOT_FOUND_EXCEPTION_CODE);
		}

	}
}
