package org.tinygroup.weixin.replymessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 音乐回复消息
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class MusicReplyMessage extends CommonReplyMessage{

	public MusicReplyMessage(){
		setMsgType("music");
	}
	
	@XStreamAlias("Music")
	private MusicItem music;

	public MusicItem getMusic() {
		return music;
	}

	public void setMusic(MusicItem music) {
		this.music = music;
	}
	
	public String toString(){
		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(getClass());
		return xstream.toXML(this);
	}
}
