package org.tinygroup.weixinpay.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinpay.pojo.RedEnvelopeResultPojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 发送裂变微信红包结果
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class GroupRedEnvelopeResult extends RedEnvelopeResultPojo implements ToServerResult{

	/**
	 * 商品订单号
	 */
	@XStreamAlias("mch_billno")
	private String orderId;
	
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
	 * 商户号
	 */
	@XStreamAlias("mch_id")
	private String mchId;
	
	/**
	 * 公众账号ID
	 */
	@XStreamAlias("wxappid")
	private String appId;
	
	/**
	 * 接收红包的微信用户id
	 */
	@XStreamAlias("re_openid")
	private String openId;
	
	/**
	 * 红包金额，单位为分
	 */
	@XStreamAlias("total_amount")
	private int totalAmount;
	
	/**
	 * 红包发放人数
	 */
	@XStreamAlias("total_num")
	private int totalNum;
	
	/**
	 * 红包发放时间
	 */
	@XStreamAlias("send_time")
	private String sendTime;
	
	/**
	 * 红包订单Id
	 */
	@XStreamAlias("send_listid")
	private String redEnvelopeId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getRedEnvelopeId() {
		return redEnvelopeId;
	}

	public void setRedEnvelopeId(String redEnvelopeId) {
		this.redEnvelopeId = redEnvelopeId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getRandomString() {
		return randomString;
	}

	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}
	
}
