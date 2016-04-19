package org.tinygroup.weixin.event;

import org.tinygroup.weixin.util.WeiXinEventMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class ScanPushEvent extends CommonEvent{

	public ScanPushEvent(){
		super();
		setEvent(WeiXinEventMode.SCANCODE_PUSH.getEvent());
	}
	
	@XStreamAlias("EventKey")
	private String eventKey;
	
	@XStreamAlias("ScanCodeInfo")
	private ScanCodeInfo scanCodeInfo;

	public ScanCodeInfo getScanCodeInfo() {
		return scanCodeInfo;
	}

	public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
		this.scanCodeInfo = scanCodeInfo;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}
