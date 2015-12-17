package org.tinygroup.exception;

import java.util.Locale;

public class BizExecute {
	private Locale locale = new Locale("zh","CN");
	public void execute(){
		throw new BaseRuntimeException("0TE111011027","",locale);
	}

	/**
	 * 既能找到code对应msg又有默认msg
	 */
	public void executeCodeWithMsg(){
		String msg = null;
		throw new BaseRuntimeException("0TE111011027","default msg",locale);
	}

	/**
	 * 没有对应msg
	 */
	public void executeNoCodeVal(){
		String msg = null;
		throw new BaseRuntimeException("0TE111011028",msg,locale);
	}



	public void executeWithMsg(){
		throw new BaseRuntimeException("0TE111011028","default msg",locale);
	}

	public void executeNoParam(){
		String msg = null;
		throw new BaseRuntimeException("",msg,locale);
	}

	public void executeBaseException(){
		throw new BaseRuntimeException(new Exception("from throwable"));
	}

	public void executeCodeWithMsgWithThrowable(String errorCode,String errormsg,Throwable e,Object... params){
		throw new BaseRuntimeException(errorCode, errormsg,locale, e,params);
	}

	public void executeCodeMsg(String errorCode, String defaultErrorMsg, Object... params){
		throw new BaseRuntimeException(errorCode, defaultErrorMsg,locale, params);
	}


	public void executeEmptyCodeWithMsg() {
		throw new BaseRuntimeException("","default msg",locale);
	}
}
