package org.tinygroup.cepcoreimpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultThreadPoolConfig implements ThreadPoolConfig{
	public int getWorkQueueLength() {
		return workQueueLength;
	}

	public void setWorkQueueLength(int workQueueLength) {
		this.workQueueLength = workQueueLength;
	}

	private int corePoolSize = 10;
	private int maximumPoolSize = 50;
	private long keepAliveTime = 0;
	private int workQueueLength = 100;
	private TimeUnit unit = TimeUnit.MILLISECONDS;

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public TimeUnit getUnit() {
		if(unit==null){
			unit = TimeUnit.MILLISECONDS;
		}
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public BlockingQueue<Runnable> getWorkQueue() {
		return new ArrayBlockingQueue<Runnable>(workQueueLength);
	}

	public ThreadFactory getThreadFactory() {
		return null;
	}

	public RejectedExecutionHandler getHandler() {
		return new ThreadPoolExecutor.AbortPolicy();
	}

	

	
}
