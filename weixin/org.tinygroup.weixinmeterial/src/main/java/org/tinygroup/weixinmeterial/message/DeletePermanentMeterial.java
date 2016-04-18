package org.tinygroup.weixinmeterial.message;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 删除永久素材(不支持临时素材)
 * @author yancheng11334
 *
 */
public class DeletePermanentMeterial implements ToServerMessage{
	
	public DeletePermanentMeterial(){
		
	}
	
	public DeletePermanentMeterial(String mediaId){
		this.mediaId = mediaId;
	}

	@JSONField(name="media_id")
	private String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String toString(){
		ObjectToJson<DeletePermanentMeterial> json= new ObjectToJson<DeletePermanentMeterial>();
		return json.convert(this);
	}
	
	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "delMaterial";
	}
}
