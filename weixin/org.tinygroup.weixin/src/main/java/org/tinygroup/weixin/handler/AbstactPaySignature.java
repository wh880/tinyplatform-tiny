package org.tinygroup.weixin.handler;

import org.tinygroup.weixin.common.PaySignature;
import org.tinygroup.weixin.util.WeiXinSignatureUtil;

/**
 * 抽象的签名创建者
 * 
 * @author yancheng11334
 * 
 */
public abstract class AbstactPaySignature implements PaySignature {

	public String createSignature(String key) {
		return WeiXinSignatureUtil.createPaySignature(this, key);
	}

}
