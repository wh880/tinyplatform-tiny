package org.tinygroup.cepcoreimpl;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolFactory {
	
	public static ThreadPoolExecutor getThreadPoolExecutor(
			ThreadPoolConfig config) {
		if (config.getThreadFactory() == null && config.getHandler() == null) {
			return new ThreadPoolExecutor(config.getCorePoolSize(),
					config.getMaximumPoolSize(), config.getKeepAliveTime(),
					config.getUnit(), config.getWorkQueue());

		} else if (config.getThreadFactory() == null) {
			return new ThreadPoolExecutor(config.getCorePoolSize(),
					config.getMaximumPoolSize(), config.getKeepAliveTime(),
					config.getUnit(), config.getWorkQueue(),
					config.getHandler());
		} else if (config.getHandler() == null) {
			return new ThreadPoolExecutor(config.getCorePoolSize(),
					config.getMaximumPoolSize(), config.getKeepAliveTime(),
					config.getUnit(), config.getWorkQueue(),
					config.getThreadFactory());
		} else {
			return new ThreadPoolExecutor(config.getCorePoolSize(),
					config.getMaximumPoolSize(), config.getKeepAliveTime(),
					config.getUnit(), config.getWorkQueue(),
					config.getThreadFactory(), config.getHandler());
		}
	}
}
