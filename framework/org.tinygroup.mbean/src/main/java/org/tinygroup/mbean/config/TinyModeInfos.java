package org.tinygroup.mbean.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("tiny-mode-infos")
public class TinyModeInfos implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@XStreamImplicit
	private List<TinyModeInfo> modeList;
	
	public List<TinyModeInfo> getModeList() {
		if (modeList == null)
			modeList = new ArrayList<TinyModeInfo>();
		return modeList;
	}

	public void setModeList(List<TinyModeInfo> serviceParameters) {
		this.modeList = serviceParameters;
	}
}
