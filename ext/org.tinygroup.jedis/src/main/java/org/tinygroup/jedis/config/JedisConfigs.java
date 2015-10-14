package org.tinygroup.jedis.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("jedis-configs")
public class JedisConfigs {

	@XStreamImplicit
	private List<JedisConfig> jedisConfigList;

	public List<JedisConfig> getJedisConfigList() {
		if(jedisConfigList==null){
			jedisConfigList = new ArrayList<JedisConfig>();
		}
		return jedisConfigList;
	}

	public void setJedisConfigList(List<JedisConfig> jedisConfigList) {
		this.jedisConfigList = jedisConfigList;
	}
	
}
