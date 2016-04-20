package org.tinygroup.weixinpay.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinpay.pojo.RedEnvelopeResultPojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 查询微信红包结果
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class QueryRedEnvelopeResult extends RedEnvelopeResultPojo implements ToServerResult{

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
	 * 接收红包的微信用户id
	 */
	@XStreamAlias("openid")
	private String openId;
	
	/**
	 * 红包状态
	 */
	private String status;
	
	/**
	 * 红包订单Id
	 */
	@XStreamAlias("detail_id")
	private String redEnvelopeId;
	
	/**
	 * 红包发送状态
	 */
	@XStreamAlias("send_type")
	private String sendType;
	
	/**
	 * 红包对应类型
	 */
	@XStreamAlias("hb_type")
	private String redEnvelopeType;
	
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
	
	/**
	 * 红包获取列表
	 */
	@XStreamAlias("hblist")
	private RedEnvelopeInfoList redEnvelopeInfoList;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRedEnvelopeId() {
		return redEnvelopeId;
	}

	public void setRedEnvelopeId(String redEnvelopeId) {
		this.redEnvelopeId = redEnvelopeId;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getRedEnvelopeType() {
		return redEnvelopeType;
	}

	public void setRedEnvelopeType(String redEnvelopeType) {
		this.redEnvelopeType = redEnvelopeType;
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

	public RedEnvelopeInfoList getRedEnvelopeInfoList() {
		return redEnvelopeInfoList;
	}

	public void setRedEnvelopeInfoList(RedEnvelopeInfoList redEnvelopeInfoList) {
		this.redEnvelopeInfoList = redEnvelopeInfoList;
	}
	
}
