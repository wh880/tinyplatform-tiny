package org.tinygroup.weixinuser.result;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class UserListItem {

	@JSONField(name="openid")
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
}
