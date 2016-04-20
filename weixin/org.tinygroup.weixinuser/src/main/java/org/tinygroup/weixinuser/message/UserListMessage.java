package org.tinygroup.weixinuser.message;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 获取用户列表的请求对象
 * @author yancheng11334
 *
 */
public class UserListMessage implements ToServerMessage{

    String nextOpenId;

	public String getNextOpenId() {
		return nextOpenId;
	}

	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "getUserList";
	}
}
