package org.tinygroup.weixintool.message;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 长链接转短链接
 * @author yancheng11334
 *
 */
public class ShortUrl implements ToServerMessage{

	private String action;
	
	@JSONField(name="long_url")
	private String longUrl;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	
	public String toString(){
		ObjectToJson<ShortUrl> json= new ObjectToJson<ShortUrl>();
		return json.convert(this);
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "shorturl";
	}

}
