package org.tinygroup.weixinpay.message;

import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.handler.AbstactPaySignature;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 查询微信红包<br>
 * 详细字段参考：https://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=16_6
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class QueryRedEnvelope extends AbstactPaySignature implements ToServerMessage{

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
	@XStreamAlias("appid")
	private String appId;
	
	
	/**
	 * 账单类型
	 */
	@XStreamAlias("bill_type")
	private String billType;
	

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

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getWeiXinKey() {
		return "queryRedEnvelope";
	}
}
