package org.tinygroup.weixinmeterial.message;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;

/**
 * 图文素材列表请求消息
 * @author yancheng11334
 *
 */
public class NewsListMessage extends GetOtherListMessage{

	public NewsListMessage(){
		setType("news");
	}
	
	public String toString(){
		ObjectToJson<NewsListMessage> json= new ObjectToJson<NewsListMessage>();
		return json.convert(this);
	}
}
