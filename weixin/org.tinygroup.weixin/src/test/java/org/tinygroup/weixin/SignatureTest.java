package org.tinygroup.weixin;

import org.tinygroup.weixin.pojo.ScanPayQueryReqData;
import org.tinygroup.weixin.util.WeiXinSignatureUtil;

import junit.framework.TestCase;

/**
 * 签名工具测试
 * @author yancheng11334
 *
 */
public class SignatureTest extends TestCase{

	/**
	 * 验证微信服务器有效性
	 */
	public void testServiceSignature(){
		String token ="111111";
		String timestamp="1371608072";
		String nonce = "1372170854";
		assertEquals("f86944503c10e7caefe35d6bc19a67e6e8d0e564", WeiXinSignatureUtil.createSignature(token, timestamp, nonce));
	}
	
	/**
	 * 验证商户平台的签名算法
	 */
	public void testPaySignature(){
		ScanPayQueryReqData data = new ScanPayQueryReqData();
		data.setAppId("wx2421b1c4370ec43b");
		data.setMchId("10000100");
		data.setTransactionId("1008450740201411110005820873");
		data.setNonceStr("ec2316275641faa3aacf3cc599e8730f");
		
		String key="abc12345678";
		
		assertEquals("81A8BCBC9E509CFDF48A4644668A3B1D",WeiXinSignatureUtil.createPaySignature(data, key));
	}
	
	/**
	 * 验证JS-SDK使用权限签名算法
	 */
	public void testJsApiSignature(){
		String noncestr ="Wm3WZYTPz0wzccnW";
		String ticket="sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg";
		String timestamp = "1414587457";
		String url ="http://mp.weixin.qq.com?params=value";
		
		assertEquals("0f9de62fce790f9a083d5c99e95740ceb90c27ed",WeiXinSignatureUtil.createJsApiSignature(noncestr, ticket, timestamp, url));
	}
}
