package org.tinygroup.weixinmeterial.result;

import java.util.List;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 视频、图片、语音列表请求结果
 * @author yancheng11334
 *
 */
public class OtherListResult implements ToServerResult{

	//总记录数
	private int totalNum;
	
	//本批记录数
	private int itemNum;
	
	private List<OtherListItem> items;
	
	
	@JSONField(name="item")
	public List<OtherListItem> getItems() {
		return items;
	}

	@JSONField(name="item")
	public void setItems(List<OtherListItem> items) {
		this.items = items;
	}

	@JSONField(name="total_count")
	public int getTotalNum() {
		return totalNum;
	}

	@JSONField(name="total_count")
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	@JSONField(name="item_count")
	public int getItemNum() {
		return itemNum;
	}

	@JSONField(name="item_count")
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	
}
