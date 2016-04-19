package org.tinygroup.weixingroupmessage.item;

import com.alibaba.fastjson.annotation.JSONField;

public class FilterJsonItem {

	@JSONField(name="group_id")
	private String groupId;
	
	@JSONField(name="is_to_all")
	private boolean toAll;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isToAll() {
		return toAll;
	}

	public void setToAll(boolean toAll) {
		this.toAll = toAll;
	}
}
