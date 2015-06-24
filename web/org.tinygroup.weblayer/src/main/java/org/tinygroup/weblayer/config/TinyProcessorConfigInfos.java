package org.tinygroup.weblayer.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;


/**
 * TinyProcessor的配置信息
 * @author renhui
 *
 */
@XStreamAlias("tiny-processors")
public class TinyProcessorConfigInfos {

	@XStreamImplicit
	private List<TinyProcessorConfigInfo> configInfos;

	public List<TinyProcessorConfigInfo> getConfigInfos() {
		if(configInfos==null){
			configInfos=new ArrayList<TinyProcessorConfigInfo>();
		}
		return configInfos;
	}

	public void setConfigInfos(List<TinyProcessorConfigInfo> configInfos) {
		this.configInfos = configInfos;
	}
	
}
