package org.tinygroup.weixinmenu.message;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 读取自定义菜单
 * @author yancheng11334
 *
 */
public class GetMenu implements ToServerMessage{

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "getMenu";
	}

}
