/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.tinypc.dlock;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by luoguo on 14-1-9.
 */
public class DistributedLockImpl extends UnicastRemoteObject implements
		DistributedLock {

	/**
	 * 
	 */
	private static final long serialVersionUID = -909469494489599236L;
	/**
	 * 锁的令牌
	 */
	private volatile long token = 0;
	/**
	 * 同步对象
	 */
	byte[] lock = new byte[0];
	/**
	 * 超时单位
	 */
	private TimeUnit lockTimeoutUnit = TimeUnit.SECONDS;
	/**
	 * 默认永不超时，默认超时3600秒,超过该时间后认为锁已解
	 */
	long lockTimeout = 60 * 60;
	long beginLockTime;// 获取令牌时间，单位毫秒

	public DistributedLockImpl() throws RemoteException {
		super();
	}

	/**
	 * @param lockTimeout
	 *            锁超时时间，如果加锁的对象不解锁，超时之后自动解锁
	 * @param lockTimeoutUnit
	 *            锁超时单位
	 * @throws RemoteException
	 */
	public DistributedLockImpl(long lockTimeout, TimeUnit lockTimeoutUnit)
			throws RemoteException {
		super();
		this.lockTimeout = lockTimeout;
		this.lockTimeoutUnit = lockTimeoutUnit;
	}

	public long lock() throws TimeoutException {
		return tryLock(0, TimeUnit.MILLISECONDS);
	}

	private boolean isLockTimeout() {
		if (lockTimeout <= 0) {
			return false;
		}
		return (System.currentTimeMillis() - beginLockTime) < lockTimeoutUnit
				.toMillis(lockTimeout);
	}

	private long getToken() {
		beginLockTime = System.currentTimeMillis();
		token = System.nanoTime();
		return token;
	}

	public long tryLock(long time, TimeUnit unit) throws TimeoutException {
		synchronized (lock) {
			long startTime = System.nanoTime();
			while (token != 0 && isLockTimeout()) {
				try {
					if (time > 0) {
						long endTime = System.nanoTime();
						if (endTime - startTime >= unit.toMillis(time)) {
							throw new TimeoutException();
						}
					}
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// DO Noting
				}
			}
			return getToken();
		}
	}

	public void unlock(long token) {
		if (this.token != 0 && token == this.token) {
			this.token = 0;
		} else {
			throw new RuntimeException("令牌" + token + "无效.");
		}
	}
}
