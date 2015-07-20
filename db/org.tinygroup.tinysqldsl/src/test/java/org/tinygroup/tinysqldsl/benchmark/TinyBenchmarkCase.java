package org.tinygroup.tinysqldsl.benchmark;

import org.tinygroup.tinysqldsl.Insert;

/**
 * 
 * @author Young
 *
 */
public abstract class TinyBenchmarkCase {

	private final String name;

	public TinyBenchmarkCase(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void execute(Insert insert) throws Exception;
}
