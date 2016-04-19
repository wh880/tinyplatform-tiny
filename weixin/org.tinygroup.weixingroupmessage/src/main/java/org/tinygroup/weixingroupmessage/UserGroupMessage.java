package org.tinygroup.weixingroupmessage;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * OpenId列表群发消息
 * @author yancheng11334
 *
 */
public class UserGroupMessage extends CommonGroupMessage{

	@JSONField(name="touser")
	private List<String> openIds;

	public List<String> getOpenIds() {
		if(openIds==null){
			openIds = new ArrayList<String>();
		}
		return openIds;
	}

	public void setOpenIds(List<String> openIds) {
		this.openIds = openIds;
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "sendByOpenId";
	}
}
