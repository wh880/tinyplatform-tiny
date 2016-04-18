package org.tinygroup.weixinpay;

import org.tinygroup.weixin.util.WeiXinSignatureUtil;
import org.tinygroup.weixinpay.message.UnityCreateOrder;
import org.tinygroup.weixinpay.pojo.ChoosePayRequest;

import junit.framework.TestCase;

/**
 * 签名的测试
 * @author yancheng11334
 *
 */
public class SignatureTest extends TestCase{

	/**
	 * 测试统一下单的动态签名
	 */
	public void testUnityCreateOrder(){
		UnityCreateOrder order = new UnityCreateOrder();
		order.setAppId("wx2421b1c4370ec43b");
		order.setAdditionalData("支付测试");
		order.setShortDescription("JSAPI支付测试");
		order.setMchId("10000100");
		order.setRandomString("1add1a30ac87aa2db72f57a2375d8fec");
		order.setNotifyUrl("http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php");
		order.setOpenId("oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");
		order.setOrderId("1415659990");
		order.setTerminalIp("14.23.150.211");
		order.setTotalAmount(1);
		order.setTradeType("JSAPI");
		System.out.println(order);
		
		assertNull(order.getSignature());
		String signature = order.createSignature("abct123");
		assertEquals("029A143D40192A41F5562D5C68BB331F", signature);
	}
	
	/**
	 * 测试微信提交支付的测试用例
	 */
	public void testChoosePayRequest(){
		ChoosePayRequest request = new ChoosePayRequest();
		request.setAppId("wx325952ab42939270");
		request.setNonceStr("9876543210");
		request.setPrepayId("prepay_id=wx201512101533157829e938230509763133");  //微信比较奇葩，需要增加prepay_id=
		request.setSignType("MD5");
		request.setTimeStamp("1449732795");
		
		String signature = WeiXinSignatureUtil.createPaySignature(request, "abcdefg");
		
		assertEquals("82195C41954C5D5C8516B3A1B224BBDA", signature);
	}

}
