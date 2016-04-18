package org.tinygroup.weixinpay.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付通讯结果模型
 * @author yancheng11334
 *
 */
public class CommunicationResultPojo{

	/**
	 * 返回状态码
	 */
	@XStreamAlias("return_code")
	private String returnCode;
	
	/**
	 * 返回消息
	 */
	@XStreamAlias("return_msg")
	private String returnMsg;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	
}
