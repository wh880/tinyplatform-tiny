package org.tinygroup.weixinpay.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinpay.pojo.BusinessResultPojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 申请退款结果
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class RefundResult extends BusinessResultPojo implements ToServerResult{

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
	 * 商品退款号
	 */
	@XStreamAlias("out_refund_no")
	private String outRefundId;
	
	/**
	 * 微信退款号
	 */
	@XStreamAlias("refund_id")
	private String refundId;
	
	/**
	 * 退款渠道
	 */
	@XStreamAlias("refund_channel")
	private String refundChannel;
	
	/**
	 * 总金额，单位为分
	 */
	@XStreamAlias("total_fee")
	private int totalAmount;
	
	/**
	 * 退款金额，单位为分
	 */
	@XStreamAlias("refund_fee")
	private int refundAmount;
	
	/**
	 * 订单金额货币种类
	 */
	@XStreamAlias("fee_type")
	private String orderFeeType;
	
	/**
	 * 现金支付金额，单位为分
	 */
	@XStreamAlias("cash_fee")
	private int cashAmount;
	
	/**
	 * 现金退款金额，单位为分
	 */
	@XStreamAlias("cash_refund_fee")
	private int cashRefundAmount;
	
	/**
	 * 代金券或立减优惠退款金额，单位为分
	 */
	@XStreamAlias("coupon_refund_fee")
	private int couponRefundAmount;
	
	/**
	 * 代金券或立减优惠使用次数
	 */
	@XStreamAlias("coupon_refund_count")
	private int couponUseNumber;
	
	/**
	 * 代金券或立减优惠Id
	 */
	@XStreamAlias("coupon_refund_id")
	private String couponRefundId;

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

	public String getOutRefundId() {
		return outRefundId;
	}

	public void setOutRefundId(String outRefundId) {
		this.outRefundId = outRefundId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(int refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getOrderFeeType() {
		return orderFeeType;
	}

	public void setOrderFeeType(String orderFeeType) {
		this.orderFeeType = orderFeeType;
	}

	public int getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(int cashAmount) {
		this.cashAmount = cashAmount;
	}

	public int getCashRefundAmount() {
		return cashRefundAmount;
	}

	public void setCashRefundAmount(int cashRefundAmount) {
		this.cashRefundAmount = cashRefundAmount;
	}

	public int getCouponRefundAmount() {
		return couponRefundAmount;
	}

	public void setCouponRefundAmount(int couponRefundAmount) {
		this.couponRefundAmount = couponRefundAmount;
	}

	public int getCouponUseNumber() {
		return couponUseNumber;
	}

	public void setCouponUseNumber(int couponUseNumber) {
		this.couponUseNumber = couponUseNumber;
	}

	public String getCouponRefundId() {
		return couponRefundId;
	}

	public void setCouponRefundId(String couponRefundId) {
		this.couponRefundId = couponRefundId;
	}
	
}
