package org.tinygroup.jedis.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("jedis-sentinel-configs")
public class JedisSentinelConfigs {
	@XStreamImplicit
	private List<JedisSentinelConfig> jedisSentinelConfigsList;
	
	public List<JedisSentinelConfig> getJedisSentinelConfigsList() {
		if (jedisSentinelConfigsList == null) {
			jedisSentinelConfigsList = new ArrayList<JedisSentinelConfig>();
		}
		return jedisSentinelConfigsList;
	}

	public void setJedisSentinelConfigsList(
			List<JedisSentinelConfig> jedisSentinelConfigsList) {
		this.jedisSentinelConfigsList = jedisSentinelConfigsList;
	}

}
