package org.tinygroup.weixinmenu.message;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 删除自定义菜单
 * @author yancheng11334
 *
 */
public class DeleteMenu implements ToServerMessage{

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "deleteMenu";
	}
}
