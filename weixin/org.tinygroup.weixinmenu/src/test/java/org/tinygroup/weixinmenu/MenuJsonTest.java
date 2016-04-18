package org.tinygroup.weixinmenu;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.tinygroup.weixinmenu.button.ClickSubButton;
import org.tinygroup.weixinmenu.button.LocationSelectSubButton;
import org.tinygroup.weixinmenu.button.MultiButton;
import org.tinygroup.weixinmenu.button.PicPhotoSubButton;
import org.tinygroup.weixinmenu.button.PicSysPhotoSubButton;
import org.tinygroup.weixinmenu.button.PicWeiXinSubButton;
import org.tinygroup.weixinmenu.button.PushSubButton;
import org.tinygroup.weixinmenu.button.SingleButton;
import org.tinygroup.weixinmenu.button.ViewSubButton;
import org.tinygroup.weixinmenu.button.WaitMsgSubButton;
import org.tinygroup.weixinmenu.message.CreateMenu;

/**
 * 测试自定义菜单Json串创建
 * @author yancheng11334
 *
 */
public class MenuJsonTest extends TestCase{

	/**
	 * 微信公开文档示例1
	 */
	public void testCilckSample(){
		CreateMenu menu  = new CreateMenu();
		SingleButton b1= new SingleButton("今日歌曲","click","V1001_TODAY_MUSIC");
		menu.addButton(b1);
		
		MultiButton b2 = new MultiButton("菜单");
		b2.addSubButton(new ViewSubButton("搜索","http://www.soso.com/"));
		b2.addSubButton(new ViewSubButton("视频","http://v.qq.com/"));
		b2.addSubButton(new ClickSubButton("赞一下我们","V1001_GOOD"));
		menu.addButton(b2);
		
		Assert.assertEquals("{\"button\":[{\"key\":\"V1001_TODAY_MUSIC\",\"name\":\"今日歌曲\",\"type\":\"click\"},{\"name\":\"菜单\",\"sub_button\":[{\"name\":\"搜索\",\"type\":\"view\",\"url\":\"http://www.soso.com/\"},{\"name\":\"视频\",\"type\":\"view\",\"url\":\"http://v.qq.com/\"},{\"key\":\"V1001_GOOD\",\"name\":\"赞一下我们\",\"type\":\"click\"}]}]}", menu.toString());
		//System.out.println(menu);
	}
	
	/**
	 * 微信公开文档示例2
	 */
    public void testOtherSample(){
    	CreateMenu menu  = new CreateMenu();
    	MultiButton b1 = new MultiButton("扫码");
    	b1.addSubButton(new PushSubButton("扫码带提示","rselfmenu_0_0"));
    	b1.addSubButton(new WaitMsgSubButton("扫码推事件","rselfmenu_0_1"));
    	
    	MultiButton b2 = new MultiButton("发图");
    	b2.addSubButton(new PicSysPhotoSubButton("系统拍照发图","rselfmenu_1_0"));
    	b2.addSubButton(new PicPhotoSubButton("拍照或者相册发图","rselfmenu_1_1"));
    	b2.addSubButton(new PicWeiXinSubButton("微信相册发图","rselfmenu_1_2"));
    	
    	MultiButton b3 = new MultiButton("其他");
    	b3.addSubButton(new LocationSelectSubButton("发送位置","rselfmenu_2_0"));
    	//b3.addSubButton(new MediaIdSubButton("图片","aE_ebp9tpjp5kp61A0zzYqiPlJDLv0JTD_nyHe0DbdY"));
    	//b3.addSubButton(new ViewLimitSubButton("图文消息","aE_ebp9tpjp5kp61A0zzYqiPlJDLv0JTD_nyHe0DbdY"));
    	
    	menu.addButton(b1);
    	menu.addButton(b2);
    	menu.addButton(b3);
    	
    	Assert.assertEquals("{\"button\":[{\"name\":\"扫码\",\"sub_button\":[{\"key\":\"rselfmenu_0_0\",\"name\":\"扫码带提示\",\"type\":\"scancode_push\"},{\"key\":\"rselfmenu_0_1\",\"name\":\"扫码推事件\",\"type\":\"scancode_waitmsg\"}]},{\"name\":\"发图\",\"sub_button\":[{\"key\":\"rselfmenu_1_0\",\"name\":\"系统拍照发图\",\"type\":\"pic_sysphoto\"},{\"key\":\"rselfmenu_1_1\",\"name\":\"拍照或者相册发图\",\"type\":\"pic_photo_or_album\"},{\"key\":\"rselfmenu_1_2\",\"name\":\"微信相册发图\",\"type\":\"pic_weixin\"}]},{\"name\":\"其他\",\"sub_button\":[{\"key\":\"rselfmenu_2_0\",\"name\":\"发送位置\",\"type\":\"location_select\"}]}]}", menu.toString());
    	//System.out.println(menu);
	}
}
