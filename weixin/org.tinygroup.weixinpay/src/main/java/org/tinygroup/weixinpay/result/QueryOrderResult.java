package org.tinygroup.weixinpay.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinpay.pojo.BusinessResultPojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 查询订单的反馈结果
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class QueryOrderResult extends BusinessResultPojo implements ToServerResult{
	
	/**
	 * 设备号
	 */
	@XStreamAlias("device_info")
	private String deviceId;
	
	/**
	 * 用户标识
	 */
	@XStreamAlias("openid")
	private String openId;
	
	/**
	 * 是否关注
	 */
	@XStreamAlias("is_subscribe")
	private String isSubscribe;
	
	/**
	 * 交易类型
	 */
	@XStreamAlias("trade_type")
	private String tradeType;
	
	/**
	 * 交易状态
	 */
	@XStreamAlias("trade_state")
	private String tradeState;
	
	/**
	 * 付款银行类型
	 */
	@XStreamAlias("bank_type")
	private String bankType;
	
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
	 * 商品订单号
	 */
	@XStreamAlias("out_trade_no")
	private String orderId;
	
	/**
	 * 微信订单号
	 */
	@XStreamAlias("transaction_id")
	private String transactionId;
	
	/**
	 * 附加数据
	 */
	@XStreamAlias("attach")
	private String additionalData;
	
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
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
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
	
	
}
