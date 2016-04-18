package org.tinygroup.weixinkf.kfmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixinkf.kfmessage.item.MusicJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服音乐消息Json包装
 * @author yancheng11334
 *
 */
public class MusicKfMessage extends CommonKfMessage{

	public MusicKfMessage(){
		setMsgType("music");
	}
	
	@JSONField(name="music")
	private MusicJsonItem musicJsonItem;
	
	public MusicJsonItem getMusicJsonItem() {
		return musicJsonItem;
	}

	public void setMusicJsonItem(MusicJsonItem musicJsonItem) {
		this.musicJsonItem = musicJsonItem;
	}

	public String toString(){
		ObjectToJson<MusicKfMessage> json= new ObjectToJson<MusicKfMessage>();
		return json.convert(this);
	}
}
