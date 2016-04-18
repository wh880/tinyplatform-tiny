package org.tinygroup.weixintest;


import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.weixin.WeiXinConnector;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.impl.WeiXinContextDefault;
import org.tinygroup.weixin.result.AccessToken;
import org.tinygroup.weixin.result.ErrorResult;
import org.tinygroup.weixin.result.JsApiTicket;
import org.tinygroup.weixinkf.kfaccount.CustomerServiceAccount;
import org.tinygroup.weixinkf.kfaccount.GetCustomerAccountList;
import org.tinygroup.weixinkf.kfmessage.TextKfMessage;
import org.tinygroup.weixinkf.kfmessage.item.TextJsonItem;
import org.tinygroup.weixinkf.result.CustomerAccountListResult;

/**
 * 客服消息测试
 * @author yancheng11334
 *
 */
public class KfMessageTest {

	private static WeiXinConnector weiXinConnector;
	static{
		AbstractTestUtil.init(null, false);
		weiXinConnector = BeanContainerFactory.getBeanContainer(KfMessageTest.class.getClassLoader()).getBean("weiXinConnector");
	}
	
	//注意客户接口回复消息是有时间限制的
	public static void main(String[] args){
		AccessToken token =weiXinConnector.getAccessToken();
		Assert.assertNotNull(token);
		
		JsApiTicket ticket = weiXinConnector.getJsApiTicket();
		Assert.assertNotNull(ticket);
		
		sendTextMessage();
		getKfList();
	}
	
	//测试发送接口
	public static void sendTextMessage(){
		
		TextKfMessage message = new TextKfMessage();
		message.setToUser("oH7YcuDMbvNuwRMpWRu5BNOS21vU");
		
		TextJsonItem item = new TextJsonItem();
		item.setContent("一二三四五");
		message.setTextJsonItem(item);
		
		ErrorResult result= send(message);
		Assert.assertNotNull(result);
		
		CustomerServiceAccount account = new CustomerServiceAccount();
		account.setAccount("test2@Tiny_Framework");
		message.setKfAccount(account);
		
		result= send(message);
		Assert.assertNotNull(result);
	}
	
	//测试获取全部客服列表接口
	public static void getKfList(){
		CustomerAccountListResult result = send(new GetCustomerAccountList());
		Assert.assertNotNull(result);
	}
	
	private static <OUTPUT> OUTPUT send(ToServerMessage message){
		WeiXinContext context = new WeiXinContextDefault();
		context.put(WeiXinConnector.ACCESS_TOKEN, weiXinConnector.getAccessToken().getAccessToken());
		weiXinConnector.getWeiXinSender().send(message,context);
		return context.getOutput();
	}
}
