package org.tinygroup.database.config.sequence;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("seq-cache-config")
public class SeqCacheConfig {

	@XStreamAsAttribute
	private boolean cache;

	@XStreamAsAttribute
	private int number=20;//默认缓存20

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
