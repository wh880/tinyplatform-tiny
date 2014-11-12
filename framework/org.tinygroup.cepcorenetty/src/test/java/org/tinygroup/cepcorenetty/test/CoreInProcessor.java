package org.tinygroup.cepcorenetty.test;

import java.util.ArrayList;
import java.util.List;

public class CoreInProcessor implements Core{
	private List<Processor> processors = new ArrayList<Processor>();

	public void addProcessor(Processor processor) {
		processors.add(processor);

	}

	public Processor getProcessor(String name) {
		List<Processor> l = new ArrayList<Processor>();
		for (Processor t : processors) {
			if (t.contain(name)) {
				l.add(t);
			}
		}
		return l.get(0);
	}

	public String get(String name) {
		Processor t = getProcessor(name);
		if (t != null) {
			return t.get(name);
		}
		return null;
	}
}
