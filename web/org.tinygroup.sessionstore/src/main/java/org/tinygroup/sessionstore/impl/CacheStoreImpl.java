package org.tinygroup.sessionstore.impl;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.tinygroup.cache.Cache;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.sessionstore.CacheStore;
import org.tinygroup.weblayer.webcontext.session.SessionConfig;

public class CacheStoreImpl implements CacheStore {

	private Cache cache;
	
	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	public void init(String storeName, SessionConfig sessionConfig)
			throws Exception {
		Assert.assertNotNull(cache, "cache is required");
	}

	public Iterable<String> getAttributeNames(String sessionID,
			StoreContext storeContext) {
		Set<String> sessionData = cache.getGroupKeys(sessionID);
		if (sessionData == null) {
			return Collections.emptyList();
		} else {
			return sessionData;
		}
	}

	public Object loadAttribute(String attrName, String sessionID,
			StoreContext storeContext) {
		return cache.get(sessionID, attrName);
	}

	public void invaldiate(String sessionID, StoreContext storeContext) {
        cache.cleanGroup(sessionID);
	}

	public void commit(Map<String, Object> modifiedAttrs, String sessionID,
			StoreContext storeContext) {
		  for (Map.Entry<String, Object> entry : modifiedAttrs.entrySet()) {
	            String attrName = entry.getKey();
	            Object attrValue = entry.getValue();

	            if (attrValue == null) {
	                cache.remove(attrName);
	            } else {
	                cache.put(sessionID,attrName, attrValue);
	            }
	        }
		
	}

}
