package org.tinygroup.weixinpay.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 基本红包业务值通用对象
 * @author yancheng11334
 *
 */
public class RedEnvelopeResultPojo extends CommunicationResultPojo{

	/**
	 * 业务代码
	 */
	@XStreamAlias("result_code")
	private String businessCode;
	
	/**
	 * 错误代码
	 */
	@XStreamAlias("err_code")
	private String errorCode;
	
	/**
	 * 错误代码描述
	 */
	@XStreamAlias("err_code_des")
	private String errorMsg;

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
