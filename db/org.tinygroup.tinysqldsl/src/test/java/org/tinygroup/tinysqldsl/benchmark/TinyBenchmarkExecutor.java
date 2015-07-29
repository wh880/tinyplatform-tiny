/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.tinysqldsl.benchmark;

import java.lang.management.ManagementFactory;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.tinygroup.tinysqldsl.Insert;
import org.tinygroup.tinysqldsl.base.StatementSqlBuilder;

public class TinyBenchmarkExecutor {

	private int loopCount = 1000;
	private int executeCount = 10;
	private final List<Insert> insertlist = new ArrayList<Insert>();
	private final List<TinyBenchmarkCase> caseList = new ArrayList<TinyBenchmarkCase>();

	public List<TinyBenchmarkCase> getCaseList() {
		return caseList;
	}

	public List<Insert> getInsertList() {
		return insertlist;
	}

	public int getLoopCount() {
		return loopCount;
	}

	public int getExecuteCount() {
		return executeCount;
	}

	public void setExecuteCount(int executeCount) {
		this.executeCount = executeCount;
	}

	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	public void execute() {
		System.out.println(System.getProperty("java.vm.name") + " "
				+ System.getProperty("java.runtime.version"));
		for (Insert insert : insertlist) {
			for (int i = 0; i < executeCount; ++i) {
				Result result = executeLoop(insert);
				handleResult(insert, result);
			}
			System.out.println();
		}
	}

	public void handleResult(StatementSqlBuilder insert, Result result) {
		if (result.getError() != null) {
			result.getError().printStackTrace();
			return;
		}
		NumberFormat format = NumberFormat.getInstance();
		System.out.println(result.getName() + "\t"
				+ format.format(result.getMillis()) + "\tYGC "
				+ result.getYoungGC() + "\tYGCT " + result.getYoungGCTime());
	}

	private Result executeLoop(Insert codec) {
		long startMillis = System.currentTimeMillis();
		long startYoungGC = getYoungGC();
		long startYoungGCTime = getYoungGCTime();
		long startFullGC = getFullGC();

		Throwable error = null;
		try {
			for (int i = 0; i < loopCount; ++i) {
				codec.sql().contentEquals(
						"INSERT INTO custom (name, age) VALUES(?, ?)");
			}
		} catch (Throwable e) {
			error = e;
		}
		long time = System.currentTimeMillis() - startMillis;
		long youngGC = getYoungGC() - startYoungGC;
		long youngGCTime = getYoungGCTime() - startYoungGCTime;
		long fullGC = getFullGC() - startFullGC;

		Result result = new Result();
		result.setName(codec.sql());
		result.setMillis(time);
		result.setYoungGC(youngGC);
		result.setYoungGCTime(youngGCTime);
		result.setFullGC(fullGC);
		result.setError(error);

		return result;
	}

	public long getYoungGC() {
		try {
			// java.lang:type=GarbageCollector,name=G1 Young Generation
			// java.lang:type=GarbageCollector,name=G1 Old Generation
			MBeanServer mbeanServer = ManagementFactory
					.getPlatformMBeanServer();
			ObjectName objectName;
			if (mbeanServer.isRegistered(new ObjectName(
					"java.lang:type=GarbageCollector,name=ParNew"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=ParNew");
			} else if (mbeanServer.isRegistered(new ObjectName(
					"java.lang:type=GarbageCollector,name=Copy"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=Copy");
			} else if (mbeanServer
					.isRegistered(new ObjectName(
							"java.lang:type=GarbageCollector,name=G1 Young Generation"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=G1 Young Generation");
			} else {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=PS Scavenge");
			}

			return (Long) mbeanServer.getAttribute(objectName,
					"CollectionCount");
		} catch (Exception e) {
			throw new RuntimeException("error");
		}
	}

	public long getYoungGCTime() {
		try {
			MBeanServer mbeanServer = ManagementFactory
					.getPlatformMBeanServer();
			ObjectName objectName;
			if (mbeanServer.isRegistered(new ObjectName(
					"java.lang:type=GarbageCollector,name=ParNew"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=ParNew");
			} else if (mbeanServer.isRegistered(new ObjectName(
					"java.lang:type=GarbageCollector,name=Copy"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=Copy");
			} else if (mbeanServer
					.isRegistered(new ObjectName(
							"java.lang:type=GarbageCollector,name=G1 Young Generation"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=G1 Young Generation");
			} else {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=PS Scavenge");
			}

			return (Long) mbeanServer
					.getAttribute(objectName, "CollectionTime");
		} catch (Exception e) {
			throw new RuntimeException("error", e);
		}
	}

	public long getFullGC() {
		try {
			MBeanServer mbeanServer = ManagementFactory
					.getPlatformMBeanServer();
			ObjectName objectName;

			if (mbeanServer
					.isRegistered(new ObjectName(
							"java.lang:type=GarbageCollector,name=ConcurrentMarkSweep"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=ConcurrentMarkSweep");
			} else if (mbeanServer.isRegistered(new ObjectName(
					"java.lang:type=GarbageCollector,name=MarkSweepCompact"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=MarkSweepCompact");
			} else if (mbeanServer.isRegistered(new ObjectName(
					"java.lang:type=GarbageCollector,name=G1 Old Generation"))) {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=G1 Old Generation");
			} else {
				objectName = new ObjectName(
						"java.lang:type=GarbageCollector,name=PS MarkSweep");
			}

			return (Long) mbeanServer.getAttribute(objectName,
					"CollectionCount");
		} catch (Exception e) {
			throw new RuntimeException("error");
		}
	}

	public static class Result {

		private String name;
		private long millis;
		private long youngGC;
		private long youngGCTime;
		private long fullGC;
		private Throwable error;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getMillis() {
			return millis;
		}

		public void setMillis(long millis) {
			this.millis = millis;
		}

		public long getYoungGC() {
			return youngGC;
		}

		public void setYoungGC(long youngGC) {
			this.youngGC = youngGC;
		}

		public long getYoungGCTime() {
			return youngGCTime;
		}

		public void setYoungGCTime(long youngGCTime) {
			this.youngGCTime = youngGCTime;
		}

		public long getFullGC() {
			return fullGC;
		}

		public void setFullGC(long fullGC) {
			this.fullGC = fullGC;
		}

		public Throwable getError() {
			return error;
		}

		public void setError(Throwable error) {
			this.error = error;
		}

	}
}
