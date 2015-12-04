package org.tinygroup.aopcache.config;

import org.tinygroup.aopcache.AopCacheProcessor;
import org.tinygroup.aopcache.base.CacheMetadata;
import org.tinygroup.aopcache.processor.AopCacheGetProcessor;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("cache-get")
public class CacheGet extends CacheAction {

	@XStreamAsAttribute
	private String key;

	@Override
	public Class<? extends AopCacheProcessor> bindAopProcessType() {
		return AopCacheGetProcessor.class;
	}

	@Override
	public CacheMetadata createMetadata() {
		CacheMetadata metadata = super.createMetadata();
		metadata.setKeys(key);
		return metadata;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
