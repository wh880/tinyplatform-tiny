package org.tinygroup.weixintest;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.weixin.WeiXinConnector;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.common.Client;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.impl.WeiXinContextDefault;
import org.tinygroup.weixin.result.AccessToken;
import org.tinygroup.weixinpay.message.DownloadBill;
import org.tinygroup.weixinpay.message.UnityCreateOrder;
import org.tinygroup.weixinpay.result.BusinessResult;
import org.tinygroup.weixinpay.result.DownloadBillResult;

public class WeiXinPayTest {

	private static WeiXinConnector weiXinConnector;
	static{
		AbstractTestUtil.init(null, false);
		weiXinConnector = BeanContainerFactory.getBeanContainer(WeiXinPayTest.class.getClassLoader()).getBean("weiXinConnector");
	}
	
	public static void main(String[] args){
		
		AccessToken token = weiXinConnector.getAccessToken();
		Assert.assertNotNull(token);
		
		testUnityCreateOrder();
		
		testDownloadBill();
	}
	
	public static void testDownloadBill(){
		DownloadBill bill = new DownloadBill();
		bill.setAppId("wx325952ab42939270");
		bill.setMchId("1244859502");
		bill.setRandomString("0b9f35f484df17a732e537c37708d1d0");
		bill.setBillDate("20151218");
		bill.setBillType("ALL");
		DownloadBillResult result = send(bill);
		Assert.assertNotNull(result);
	}
	
	public static void testUnityCreateOrder(){
		UnityCreateOrder order = new UnityCreateOrder();
		order.setAppId("wx325952ab42939270");
		order.setAdditionalData("支付测试");
		order.setShortDescription("JSAPI支付测试");
		order.setMchId("1244859502");
		order.setRandomString("1add1a30ac87aa2db72f57a2375d8fec");
		order.setNotifyUrl("http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php");
		order.setOpenId("oH7YcuDMbvNuwRMpWRu5BNOS21vU");
		order.setOrderId("1415659990");
		order.setTotalAmount(1);
		order.setTerminalIp("14.23.150.211");
		order.setTradeType("JSAPI");
		
		
		BusinessResult result = send(order);
		Assert.assertNotNull(result);
		Assert.assertTrue("SUCCESS".equals(result.getReturnCode()));
	}
	
	private static <OUTPUT> OUTPUT send(ToServerMessage message){
		WeiXinContext context = new WeiXinContextDefault();
		context.put(WeiXinConnector.ACCESS_TOKEN, weiXinConnector.getAccessToken().getAccessToken());
		context.put(Client.DEFAULT_CONTEXT_NAME, weiXinConnector.getClient());
		weiXinConnector.getWeiXinSender().send(message,context);
		return context.getOutput();
	}
	
}
