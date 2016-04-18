package org.tinygroup.weixinpay.message;

import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.handler.AbstactPaySignature;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 发送裂变微信红包<br>
 * 详细字段参考：https://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=16_5
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class GroupRedEnvelope extends AbstactPaySignature implements ToServerMessage{

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
	 * 商品订单号
	 */
	@XStreamAlias("mch_billno")
	private String orderId;
	
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
	 * 发送红包的商户名称
	 */
	@XStreamAlias("send_name")
	private String sendName;
	
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
	 * 总人数
	 */
	@XStreamAlias("total_num")
	private int totalNum;
	
	/**
	 * 红包金额设置方式
	 */
	@XStreamAlias("amt_type")
	private String amtType;
	
	/**
	 * 红包祝福语
	 */
	private String wishing;
	
	/**
	 * 红包活动名称
	 */
	@XStreamAlias("act_name")
	private String actionName;
	
	/**
	 * 备注
	 */
	private String remark;

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

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
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

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAmtType() {
		return amtType;
	}

	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}

	public String getWeiXinKey() {
		return "groupRedEnvelope";
	}

}
