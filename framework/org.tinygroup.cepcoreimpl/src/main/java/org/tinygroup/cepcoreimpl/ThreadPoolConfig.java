package org.tinygroup.cepcoreimpl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public interface ThreadPoolConfig {
	final String DEFAULT_THREADPOOL = "default_tiny_service_threadpool";
	
	public int getCorePoolSize();

	public int getMaximumPoolSize();

	public long getKeepAliveTime();

	public TimeUnit getUnit();

	public BlockingQueue<Runnable> getWorkQueue();

	public ThreadFactory getThreadFactory();

	public RejectedExecutionHandler getHandler();
}
