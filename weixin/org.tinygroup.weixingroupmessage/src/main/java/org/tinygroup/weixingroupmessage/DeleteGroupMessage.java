package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 删除群发消息
 * @author yancheng11334
 *
 */
public class DeleteGroupMessage implements ToServerMessage{

	public DeleteGroupMessage(){
		
	}
	
    public DeleteGroupMessage(String mediaId){
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
		ObjectToJson<DeleteGroupMessage> json= new ObjectToJson<DeleteGroupMessage>();
		return json.convert(this);
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "delGroupMessage";
	}
	
}
