package org.tinygroup.aopcache.config;

import org.tinygroup.aopcache.AopCacheProcessor;
import org.tinygroup.aopcache.base.CacheMetadata;
import org.tinygroup.aopcache.processor.AopCachePutProcessor;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by renhui on 2015/9/23.
 */
@XStreamAlias("cache-put")
public class CachePut extends CacheAction {
	@XStreamAsAttribute
	private String keys;//多个key以逗号分隔
	@XStreamAsAttribute
	@XStreamAlias("parameter-names")
	private String parameterNames;//多个参数名称以逗号分隔
	
	@XStreamAlias("remove-keys")
	@XStreamAsAttribute
	private String removeKeys;
	@XStreamAsAttribute
	private long expire;

	@XStreamAlias("remove-groups")
	@XStreamAsAttribute
	private String removeGroups;
	
	@Override
	public Class<? extends AopCacheProcessor> bindAopProcessType() {
		return AopCachePutProcessor.class;
	}

	@Override
	public CacheMetadata createMetadata() {
        CacheMetadata metadata=super.createMetadata();
        metadata.setKeys(keys);
        metadata.setParameterNames(parameterNames);
        metadata.setRemoveKeys(removeKeys);
        metadata.setRemoveGroups(removeGroups);
        metadata.setExpire(expire);
		return metadata;
	}

	public String getRemoveKeys() {
		return removeKeys;
	}

	public void setRemoveKeys(String removeKeys) {
		this.removeKeys = removeKeys;
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(String parameterNames) {
		this.parameterNames = parameterNames;
	}

	public String getRemoveGroups() {
		return removeGroups;
	}

	public void setRemoveGroups(String removeGroups) {
		this.removeGroups = removeGroups;
	}
	
	
}
