package org.tinygroup.logger.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.MDC;
import org.tinygroup.logger.MDCAdaptor;

/**
 * 功能说明: MDC 实现类，用于封装和隔离实际的MDC<br>
 *         该类是MDCAdaptor接口的实现，相关方法<br>
 *         注释参见接口
 * @author renhui
 */
public class BasicMDCAdapter implements MDCAdaptor {

	/** The inheritable thread local. */
	private InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal();


	public void put(String key, Object val) {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null");
		}
		HashMap map = (HashMap) inheritableThreadLocal.get();
		if (map == null) {
			map = new HashMap();
			inheritableThreadLocal.set(map);
		}
		if (val == null) {
			remove(key);
		} else {
			map.put(key, val);
			if (val != null) {
				MDC.put(key, val);
			}
		}
	}


	public Object get(String key) {
		HashMap hashMap = (HashMap) inheritableThreadLocal.get();
		if ((hashMap != null) && (key != null)) {
			return hashMap.get(key);
		} else {
			return null;
		}
	}

	public void remove(String key) {
		HashMap map = (HashMap) inheritableThreadLocal.get();
		if (map != null) {
			map.remove(key);
			MDC.remove(key);
		}
	}

	public void clear() {
		HashMap hashMap = (HashMap) inheritableThreadLocal.get();
		if (hashMap != null) {
			hashMap.clear();
			MDC.getContext().clear();
			inheritableThreadLocal.remove();
		}
	}


	/**
	 * Gets the keys.
	 * 
	 * @return the keys
	 */
	public Set getKeys() {
		HashMap hashMap = (HashMap) inheritableThreadLocal.get();
		if (hashMap != null) {
			return hashMap.keySet();
		} else {
			return null;
		}
	}


	public Map getCopyOfContextMap() {
		HashMap hashMap = (HashMap) inheritableThreadLocal.get();
		if (hashMap != null) {
			return new HashMap(hashMap);
		} else {
			return null;
		}
	}

	public void setContextMap(Map contextMap) {
		HashMap hashMap = (HashMap) inheritableThreadLocal.get();
		if (hashMap != null) {
			hashMap.clear();
			hashMap.putAll(contextMap);
		} else {
			hashMap = new HashMap(contextMap);
			inheritableThreadLocal.set(hashMap);
		}		
	}
}
