package org.tinygroup.weixin.message;

import org.tinygroup.weixin.util.WeiXinMessageMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 链接消息
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class LinkMessage extends CommonMessage{
	
	public LinkMessage(){
		setMsgType(WeiXinMessageMode.LINK.getType());
	}

	@XStreamAlias("Title")
	private String title;
	
	@XStreamAlias("Description")
	private String description;
	
	@XStreamAlias("Url")
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
