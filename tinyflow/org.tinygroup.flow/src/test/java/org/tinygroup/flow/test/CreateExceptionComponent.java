package org.tinygroup.flow.test;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.flow.test.Exception.ExceptionNew0;
import org.tinygroup.flow.test.Exception.ExceptionNew1;
import org.tinygroup.flow.test.Exception.ExceptionNew2;
import org.tinygroup.flow.test.Exception.ExceptionNew3InOtherNode;
import org.tinygroup.flow.test.Exception.ExceptionNew4InOtherFlow;

public class CreateExceptionComponent implements ComponentInterface{
	int exceptionNo;
	
	public int getExceptionNo() {
		return exceptionNo;
	}

	public void setExceptionNo(int exceptionNo) {
		this.exceptionNo = exceptionNo;
	}

	public void execute(Context context) {
		switch (exceptionNo) {
		case 0:
			throw new ExceptionNew0();
		case 1:
			throw new ExceptionNew1();
		case 2:
			throw new ExceptionNew2();	
		case 3:
			throw new ExceptionNew3InOtherNode();	
		default:
			throw new ExceptionNew4InOtherFlow();	
		}
	}

}
