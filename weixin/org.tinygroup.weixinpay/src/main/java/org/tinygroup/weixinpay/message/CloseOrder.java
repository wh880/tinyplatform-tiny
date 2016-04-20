package org.tinygroup.weixinpay.message;

import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.handler.AbstactPaySignature;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 关闭订单<br>
 * 详细字段参考：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3
 * @author yancheng11334
 *
 */
public class CloseOrder extends AbstactPaySignature implements ToServerMessage{

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
	@XStreamAlias("out_trade_no")
	private String orderId;

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

	public String getWeiXinKey() {
		return "closeOrder";
	}
	
	public String toString(){
		XStream xstream = new XStream(new XppDriver(new NoNameCoder()));
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(getClass());
		return xstream.toXML(this);
	}
}
