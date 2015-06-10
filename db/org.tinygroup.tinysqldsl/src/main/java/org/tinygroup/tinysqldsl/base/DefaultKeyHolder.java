package org.tinygroup.tinysqldsl.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DefaultKeyHolder implements DslKeyHolder {

	private final List keyList;


	/**
	 * Create a new GeneratedKeyHolder with a default list.
	 */
	public DefaultKeyHolder() {
		this.keyList = new LinkedList();
	}
	
	public DefaultKeyHolder(String keyName,Object value) {
		this.keyList = new LinkedList();
		Map<String, Object> keyMap=new HashMap<String, Object>();
		keyMap.put(keyName, value);
		keyList.add(keyMap);
	}

	/**
	 * Create a new GeneratedKeyHolder with a given list.
	 * @param keyList a list to hold maps of keys
	 */
	public DefaultKeyHolder(List keyList) {
		this.keyList = keyList;
	}


	public Number getKey() {
		if (this.keyList.size() == 0) {
			return null;
		}
		if (this.keyList.size() > 1 || ((Map) this.keyList.get(0)).size() > 1) {
			throw new RuntimeException(
					"The getKey method should only be used when a single key is returned.  " +
					"The current key entry contains multiple keys: " + this.keyList);
		}
		Iterator keyIter = ((Map) this.keyList.get(0)).values().iterator();
		if (keyIter.hasNext()) {
			Object key = keyIter.next();
			if (!(key instanceof Number)) {
				throw new RuntimeException(
						"The generated key is not of a supported numeric type. " +
						"Unable to cast [" + (key != null ? key.getClass().getName() : null) +
						"] to [" + Number.class.getName() + "]");
			}
			return (Number) key;
		}
		else {
			throw new RuntimeException("Unable to retrieve the generated key. " +
					"Check that the table has an identity column enabled.");
		}
	}

	public Map getKeys() {
		if (this.keyList.size() == 0) {
			return null;
		}
		if (this.keyList.size() > 1)
			throw new RuntimeException(
					"The getKeys method should only be used when keys for a single row are returned.  " +
					"The current key list contains keys for multiple rows: " + this.keyList);
		return (Map) this.keyList.get(0);
	}

	public List getKeyList() {
		return this.keyList;
	}

}
