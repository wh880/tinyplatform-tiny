package org.tinygroup.weixinmenu.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinmenu.message.CreateMenu;

import com.alibaba.fastjson.annotation.JSONField;

public class WeiXinMenuResult implements ToServerResult{

	@JSONField(name="menu")
	private CreateMenu weiXinMenu;

	public CreateMenu getWeiXinMenu() {
		return weiXinMenu;
	}

	public void setWeiXinMenu(CreateMenu weiXinMenu) {
		this.weiXinMenu = weiXinMenu;
	}
	
	
}
