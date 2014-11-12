package org.tinygroup.bundlejar;

import java.util.HashMap;
import java.util.Map;

public class BundleTestServic {
	Map<String, BundleTestObject> map = new HashMap<String, BundleTestObject>();

	public void put(BundleTestObject object) {
		map.put(object.getName(), object);
	}
	public BundleTestObject get(String name){
		return map.get(name);
	}

}
