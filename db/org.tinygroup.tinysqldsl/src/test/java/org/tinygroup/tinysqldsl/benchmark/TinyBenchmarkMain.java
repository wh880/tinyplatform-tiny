package org.tinygroup.tinysqldsl.benchmark;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.Insert.insertInto;

public class TinyBenchmarkMain {

	public static void main(String[] args) {
		TinyBenchmarkExecutor executor = new TinyBenchmarkExecutor();
		executor.setExecuteCount(5);
		executor.setLoopCount(1000 * 1000 * 10);
		executor.getInsertList().add(
				insertInto(CUSTOM).values(CUSTOM.NAME.value("悠然"),
						CUSTOM.AGE.value(22)));
		executor.execute();
	}
}
