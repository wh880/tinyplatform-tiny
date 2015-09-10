package org.tinygroup.exception;

public class BizExecute {
	public void execute(){
		throw new BizRuntimeException("0TE111011027");
	}
	public void executeWithMsg(){
		throw new BizRuntimeException("0TE111011028","hello error");
	}
}
