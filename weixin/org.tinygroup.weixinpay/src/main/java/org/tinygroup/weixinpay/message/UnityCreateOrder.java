package org.tinygroup.weixinpay.message;

import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.handler.AbstactPaySignature;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 统一创建订单<br>
 * 详细字段含义请参考微信支付文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class UnityCreateOrder extends AbstactPaySignature implements ToServerMessage{

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
	 * 商品简要描述，限制32个字符
	 */
	@XStreamAlias("body")
	private String shortDescription;
	
	/**
	 * 商品详细描述，限制8192个字符
	 */
	@XStreamAlias("detail")
	private String longDescription;
	
	/**
	 * 附加数据
	 */
	@XStreamAlias("attach")
	private String additionalData;
	
	/**
	 * 商品订单号
	 */
	@XStreamAlias("out_trade_no")
	private String orderId;
	
	/**
	 * 货币类型
	 */
	@XStreamAlias("fee_type")
	private String currencyType;
	
	/**
	 * 总金额，单位为分
	 */
	@XStreamAlias("total_fee")
	private int totalAmount;
	
	/**
	 * 终端IP
	 */
	@XStreamAlias("spbill_create_ip")
	private String terminalIp;
	
	/**
	 * 订单生成时间，格式为yyyyMMddHHmmss
	 */
	@XStreamAlias("time_start")
	private String createTime;
	
	/**
	 * 订单失效时间，格式为yyyyMMddHHmmss
	 */
	@XStreamAlias("time_expire")
	private String expireTime;
	
	/**
	 * 商品标记
	 */
	@XStreamAlias("goods_tag")
	private String productMarks;
	
	/**
	 * 回调URL地址
	 */
	@XStreamAlias("notify_url")
	private String notifyUrl;
	
	/**
	 * 交易类型
	 */
	@XStreamAlias("trade_type")
	private String tradeType;
	
	/**
	 * 商品Id
	 */
	@XStreamAlias("product_id")
	private String productId;
	
	/**
	 * 限定支付方式
	 */
	@XStreamAlias("limit_pay")
	private String limitPayType;
	
	/**
	 * 微信用户id
	 */
	@XStreamAlias("openid")
	private String openId;

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

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTerminalIp() {
		return terminalIp;
	}

	public void setTerminalIp(String terminalIp) {
		this.terminalIp = terminalIp;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getProductMarks() {
		return productMarks;
	}

	public void setProductMarks(String productMarks) {
		this.productMarks = productMarks;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitPayType() {
		return limitPayType;
	}

	public void setLimitPayType(String limitPayType) {
		this.limitPayType = limitPayType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWeiXinKey() {
		return "unityCreateOrder";
	}
	
	public String toString(){
		XStream xstream = new XStream(new XppDriver(new NoNameCoder()));
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(getClass());
		return xstream.toXML(this);
	}
	
}
