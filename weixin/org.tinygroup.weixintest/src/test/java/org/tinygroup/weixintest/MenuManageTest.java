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
import org.tinygroup.weixinmenu.button.ClickSubButton;
import org.tinygroup.weixinmenu.button.MultiButton;
import org.tinygroup.weixinmenu.button.ViewSubButton;
import org.tinygroup.weixinmenu.message.CreateMenu;
import org.tinygroup.weixinmenu.message.DeleteMenu;
import org.tinygroup.weixinmenu.message.GetMenu;
import org.tinygroup.weixinmenu.result.WeiXinMenuResult;

/**
 * 自定义菜单管理接口测试
 * @author yancheng11334
 *
 */
public class MenuManageTest {


	private static WeiXinConnector weiXinConnector;
	static{
		AbstractTestUtil.init(null, false);
		weiXinConnector = BeanContainerFactory.getBeanContainer(KfMessageTest.class.getClassLoader()).getBean("weiXinConnector");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		AccessToken token = weiXinConnector.getAccessToken();
		Assert.assertNotNull(token);
		
		testAddMenu(token);
		testGetMenu(token);
		testDelMenu(token); 
	}
	
	/**
	 * 测试添加自定义菜单
	 * @param token
	 */
	public static void testAddMenu(AccessToken token){
		
		CreateMenu  menu = new CreateMenu();
		MultiButton b1 = new MultiButton("休闲");
		MultiButton b2 = new MultiButton("传送门");
		MultiButton b3 = new MultiButton("选项");
		
		menu.addButton(b1);
		menu.addButton(b2);
		menu.addButton(b3);
		
		b1.addSubButton(new ClickSubButton("今日歌曲","click_0_0"));
		b1.addSubButton(new ClickSubButton("小游戏","click_0_1"));
		
		b2.addSubButton(new ViewSubButton("官网","http://www.tinygroup.org/"));
		b2.addSubButton(new ViewSubButton("社区","http://bbs.tinygroup.org/"));
		b2.addSubButton(new ViewSubButton("博客","http://my.oschina.net/tinyframework"));
		b2.addSubButton(new ViewSubButton("文档","http://www.tinygroup.org/confluence/pages/viewpage.action?pageId=557323"));
		
		b3.addSubButton(new ViewSubButton("搜索","http://www.baidu.com/"));
		b3.addSubButton(new ClickSubButton("给我们点赞","click_2_0"));
		
		ErrorResult json = send(menu);
		Assert.assertTrue(json.getErrCode().equals("0"));
	}
	
	/**
	 * 测试读写自定义菜单
	 * @param token
	 */
    public static void testGetMenu(AccessToken token){
    	
		WeiXinMenuResult json = send(new GetMenu());
		Assert.assertNotNull(json.getWeiXinMenu());
	}
    
    /**
     * 测试删除自定义菜单
     * @param token
     */
    public static void testDelMenu(AccessToken token){
    	ErrorResult json = send(new DeleteMenu());
		Assert.assertTrue(json.getErrCode().equals("0"));
	}
    
    private static <OUTPUT> OUTPUT send(ToServerMessage message){
		WeiXinContext context = new WeiXinContextDefault();
		context.put(WeiXinConnector.ACCESS_TOKEN, weiXinConnector.getAccessToken().getAccessToken());
		weiXinConnector.getWeiXinSender().send(message,context);
		return context.getOutput();
	}
    
}
