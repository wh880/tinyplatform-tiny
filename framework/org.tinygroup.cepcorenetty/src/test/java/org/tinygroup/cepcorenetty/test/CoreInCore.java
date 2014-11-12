package org.tinygroup.cepcorenetty.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreInCore implements Core{
	private List<Processor> processors = new ArrayList<Processor>();
	private Map<String, List<Processor>> map = new HashMap<String, List<Processor>>();

	public void addProcessor(Processor processor) {
		processors.add(processor);
		List<Item> list = processor.get();
		for (Item i : list) {
			String name = i.getName();
			if (map.containsKey(name)) {
				map.get(name).add(processor);
			} else {
				List<Processor> pl = new ArrayList<Processor>();
				pl.add(processor);
				map.put(name, pl);
			}
		}
	}

	public Processor getProcessor(String name) {
		if (map.containsKey(name))
			return map.get(name).get(0);
		return null;
	}
	
	public String get(String name){
		Processor t = getProcessor(name);
		if(t!=null){
			return t.get(name);
		}
		return null;
	}
}
