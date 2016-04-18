package org.tinygroup.weixingroupmessage;

import org.tinygroup.weixingroupmessage.item.FilterJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 分组群发消息
 * @author yancheng11334
 *
 */
public class FilterGroupMessage extends CommonGroupMessage{

	@JSONField(name="filter")
	private FilterJsonItem filterJsonItem;

	public FilterJsonItem getFilterJsonItem() {
		return filterJsonItem;
	}

	public void setFilterJsonItem(FilterJsonItem filterJsonItem) {
		this.filterJsonItem = filterJsonItem;
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "sendByGroup";
	}
	
}
