package org.tinygroup.weixinpay.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinpay.pojo.BusinessResultPojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 统一下单的反馈结果
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class UnityCreateOrderResult extends BusinessResultPojo implements ToServerResult{
	
	/**
	 * 交易类型
	 */
	@XStreamAlias("trade_type")
	private String tradeType;
	
	/**
	 * 预支付会话Id
	 */
	@XStreamAlias("prepay_id")
	private String prepayId;
	
	/**
	 * 二维码URL
	 */
	@XStreamAlias("code_url")
	private String codeUrl;


	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	
	
}
