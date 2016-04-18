package org.tinygroup.weixinpay.message;

import org.tinygroup.weixin.common.FromServerMessage;
import org.tinygroup.weixinpay.pojo.BusinessResultPojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 支付结果通知
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class PayResultMessage extends BusinessResultPojo implements FromServerMessage{

	/**
	 * 微信用户id
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
	 * 现金支付金额，单位为分
	 */
	@XStreamAlias("cash_fee")
	private int cashAmount;
	
	/**
	 * 现金货币类型
	 */
	@XStreamAlias("cash_fee_type")
	private String cashCurrencyType;
	
	/**
	 * 代金券或立减优惠金额，单位为分
	 */
	@XStreamAlias("coupon_fee")
	private int couponAmount;
	
	/**
	 * 代金券或立减优惠使用次数
	 */
	@XStreamAlias("coupon_count")
	private int couponNumber;
	
	
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
	 * 订单完成时间，格式为yyyyMMddHHmmss
	 */
	@XStreamAlias("time_end")
	private String endTime;

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

	public int getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(int cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getCashCurrencyType() {
		return cashCurrencyType;
	}

	public void setCashCurrencyType(String cashCurrencyType) {
		this.cashCurrencyType = cashCurrencyType;
	}

	public int getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(int couponAmount) {
		this.couponAmount = couponAmount;
	}

	public int getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(int couponNumber) {
		this.couponNumber = couponNumber;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
