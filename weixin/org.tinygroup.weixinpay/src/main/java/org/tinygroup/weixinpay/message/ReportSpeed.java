package org.tinygroup.weixinpay.message;

import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.handler.AbstactPaySignature;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 微信接口的测速报告<br>
 * 详细字段参考：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_8
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class ReportSpeed extends AbstactPaySignature implements ToServerMessage{

	/**
	 * 公众账号ID
	 */
	@XStreamAlias("appid")
	private String appId;
	
	/**
	 * 商户号
	 */
	@XStreamAlias("mch_id")
	private String mchId;
	
	/**
	 * 设备号
	 */
	@XStreamAlias("device_info")
	private String deviceId;
	
	/**
	 * 随机字符串
	 */
	@XStreamAlias("nonce_str")
	private String randomString;
	
	/**
	 * 签名
	 */
	@XStreamAlias("sign")
	private String signature;
	
	/**
	 * 接口URL
	 */
	@XStreamAlias("interface_url")
	private String interfaceUrl;
	
	/**
	 * 接口耗时，单位毫秒
	 */
	@XStreamAlias("execute_time_")
	private int executeTime;
	
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
	
	/**
	 * 商品订单号
	 */
	@XStreamAlias("out_trade_no")
	private String orderId;
	
	/**
	 * 访问接口IP
	 */
	@XStreamAlias("user_ip")
	private String userIp;
	
	/**
	 * 商户上报时间
	 */
	@XStreamAlias("time")
	private String reportTime;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRandomString() {
		return randomString;
	}

	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}
	
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public int getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(int executeTime) {
		this.executeTime = executeTime;
	}

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String toString(){
		XStream xstream = new XStream(new XppDriver(new NoNameCoder()));
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(getClass());
		return xstream.toXML(this);
	}
	
	public String getWeiXinKey() {
		return "reportSpeed";
	}
}
