package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 查询群发消息状态
 * @author yancheng11334
 *
 */
public class QueryGroupMessage implements ToServerMessage{

	public QueryGroupMessage(){
		
	}
	
    public QueryGroupMessage(String mediaId){
		this.mediaId = mediaId;
	}

	@JSONField(name="media_id")
	private  String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String toString(){
		ObjectToJson<QueryGroupMessage> json= new ObjectToJson<QueryGroupMessage>();
		return json.convert(this);
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "getMessageStatus";
	}
}
