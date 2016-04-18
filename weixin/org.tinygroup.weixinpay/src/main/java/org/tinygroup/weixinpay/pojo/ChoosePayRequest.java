package org.tinygroup.weixinpay.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 这个是用来包装微信支付的签名认证
 * @author yancheng11334
 *
 */
public class ChoosePayRequest {

	
	private String appId;
	
	
	private String timeStamp;
	
    
	@XStreamAlias("package")
	private String prepayId;
	
	private String nonceStr;
	
	
	private String signType;
	
	private String paySign;

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
