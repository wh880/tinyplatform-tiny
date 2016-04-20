package org.tinygroup.weixin.common;

/**
 * 支付签名接口
 * @author yancheng11334
 *
 */
public interface PaySignature {

	/**
	 * 如果签名字段为空，则自动调用创建签名的逻辑
	 * @param key 商户平台设置的key值
	 * @return
	 */
	String createSignature(String key);
	
	/**
	 * 获得签名
	 * @return
	 */
	String getSignature();
	
	/**
	 * 设置签名
	 * @param Signature
	 */
	void setSignature(String Signature);
}
