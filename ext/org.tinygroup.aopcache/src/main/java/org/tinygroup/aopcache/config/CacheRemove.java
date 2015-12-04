package org.tinygroup.aopcache.config;

import org.tinygroup.aopcache.AopCacheProcessor;
import org.tinygroup.aopcache.base.CacheMetadata;
import org.tinygroup.aopcache.processor.AopCacheRemoveProcessor;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("cache-remove")
public class CacheRemove extends CacheAction {
	
	@XStreamAlias("remove-keys")
	@XStreamAsAttribute
	private String removeKeys;
	
	@XStreamAlias("remove-groups")
	@XStreamAsAttribute
	private String removeGroups;

	@Override
	public Class<? extends AopCacheProcessor> bindAopProcessType() {
		return AopCacheRemoveProcessor.class;
	}

	@Override
	public CacheMetadata createMetadata() {
        CacheMetadata metadata=super.createMetadata();
        metadata.setRemoveKeys(removeKeys);
        metadata.setRemoveGroups(removeGroups);
		return metadata;
	}

	public String getRemoveKeys() {
		return removeKeys;
	}

	public void setRemoveKeys(String removeKeys) {
		this.removeKeys = removeKeys;
	}

	public String getRemoveGroups() {
		return removeGroups;
	}

	public void setRemoveGroups(String removeGroups) {
		this.removeGroups = removeGroups;
	}

}
