package org.tinygroup.cepcorenetty.test;

public interface Core  {
	void addProcessor(Processor processor);
	Processor getProcessor(String name);
	String get(String name);
}
