package org.tinygroup.weixinmeterial.message;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 视频、图片、语音列表消息对象
 * @author yancheng11334
 *
 */
public class GetOtherListMessage implements ToServerMessage{

	private String type;
	
	//从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	private int offset;
	
	//返回素材的数量，取值在1到20之间
	private int count;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "getOtherList";
	}
	
}
