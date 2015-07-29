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
package org.tinygroup.tinypc.impl;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rmi.RmiServer;
import org.tinygroup.tinypc.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoguo on 14-1-8.
 */
public class AbstractJobCenter implements JobCenter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractJobCenter.class);
	private RmiServer rmiServer;
	private WorkQueue workQueue;

	public WorkQueue getWorkQueue() {
		return workQueue;
	}

	public void setWorkQueue(WorkQueue workQueue) {
		this.workQueue = workQueue;
	}

	public RmiServer getRmiServer() {
		return rmiServer;
	}

	public void setRmiServer(RmiServer rmiServer) throws RemoteException {
		this.rmiServer = rmiServer;
		this.workQueue = rmiServer.getObject(WorkQueue.WORKQUEUE_TYPE);
	}

	public void registerWorker(Worker worker) throws RemoteException {
		registerParallelObject(Worker.WORKER_TYPE, worker);
	}

	private void registerParallelObject(String objectType,
			ParallelObject parallelObject) throws RemoteException {
		// rmiServer.registerLocalObject(parallelObject, objectType + "|"
		// + parallelObject.getType(), parallelObject.getId());
		rmiServer.registerLocalObject(parallelObject,
				getTypeName(objectType, parallelObject.getType()),
				parallelObject.getId());
	}

	private void unregisterParallelObject(String objectType,
			ParallelObject parallelObject) throws RemoteException {
		rmiServer.unregisterObject(
				getTypeName(objectType, parallelObject.getType()),
				parallelObject.getId());
	}

	public void unregisterWorker(Worker worker) throws RemoteException {
		unregisterParallelObject(Worker.WORKER_TYPE, worker);
	}

	public void registerWork(Work work) throws IOException {
		workQueue.add(work);
	}

	public void replaceWork(Work oldWork, Work newWork) throws RemoteException {
		workQueue.replace(oldWork, newWork);
	}

	public void unregisterWork(Work work) throws RemoteException {
		workQueue.remove(work);
	}

	public WorkStatus getWorkStatus(Work work) throws RemoteException {
		return workQueue.getWorkStatus(work);
	}

	public void registerForeman(Foreman foreman) throws RemoteException {
		registerParallelObject(Foreman.FOREMAN_TYPE, foreman);
	}

	public void unregisterForeMan(Foreman foreman) throws RemoteException {
		this.unregisterParallelObject(Foreman.FOREMAN_TYPE, foreman);
	}

	public List<Worker> getWorkerList(Work work) throws RemoteException {
		return rmiServer.getObjectList(getTypeName(Worker.WORKER_TYPE,
				work.getType()));
	}

	private String getTypeName(String type, String workType) {
		return type + "|" + workType;
	}

	public List<Work> getWorkList() throws RemoteException {
		return workQueue.getWorkList();
	}

	public List<Work> getWorkList(String type, WorkStatus workStatus)
			throws RemoteException {
		return workQueue.getWorkList(type, workStatus);
	}

	public List<Foreman> getForeman(String type) throws RemoteException {
		List<Foreman> foremanList = rmiServer.getObjectList(getTypeName(
				Foreman.FOREMAN_TYPE, type));
		List<Foreman> foremans = new ArrayList<Foreman>();
		for (Foreman foreman : foremanList) {
			try {
				foreman.getId();// 通过调用此方法查看此远程对象是否可用
				foremans.add(foreman);
			} catch (RemoteException e) {
				try {
					LOGGER.errorMessage("调用工头:{0}时出现异常", e, foreman.getId());
					rmiServer.unregisterObject(foreman);
				} catch (RemoteException e1) {
					LOGGER.errorMessage("注销工头:{0}时出现异常", e, foreman.getId());
				}
			}
		}
		return foremans;
	}

	public Warehouse doWork(Work work) throws IOException {
		String foremanType = work.getForemanType();
		// List<Foreman> foremanList = null;
		// foremanList = getForemans(work, foremanType);
		List<Foreman> foremanList = getForemans(work, foremanType);
		// 如果存在空闲的工头
		Foreman foreman = getForeman(foremanType, foremanList);
		// 获取所有能执行该任务的工人
		List<Worker> workers = getWorkerList(work);
		// 存放接受该work的工人列表
		List<Worker> acceptWorkers = getAcceptWorkers(work, workers);
		if (!acceptWorkers.isEmpty()) {
			Warehouse outputWarehouse = foreman.work(work,
					cloneWorkers(acceptWorkers));
			// 检查是否有子任务
			Work nextWork = work.getNextWork();
			if (nextWork != null) {
				if (nextWork.getInputWarehouse() != null) {
					nextWork.getInputWarehouse().putSubWarehouse(
							outputWarehouse);
				} else {
					nextWork.setInputWarehouse(outputWarehouse);
				}
				outputWarehouse = doWork(nextWork);
			}
			return outputWarehouse;
		} else {
			throw new PCRuntimeException(String.format("没有合适的工人来完成工作：%s %s",
					work.getType(), work.getId()));
		}

	}

	private List<Worker> getAcceptWorkers(Work work, List<Worker> workers) {
		List<Worker> acceptWorkers = new ArrayList<Worker>();
		for (Worker worker : workers) {
			try {
				// 判断该工人是否接受该任务
				if (worker.acceptWork(work)) {
					acceptWorkers.add(worker);
				}
			} catch (RemoteException e) {
				LOGGER.errorMessage("判断worker是否接受work[id:{1}]时发生异常", e,
						work.getId());
			}
		}
		return acceptWorkers;
	}

	private Foreman getForeman(String foremanType, List<Foreman> foremanList)
			throws RemoteException {
		// Foreman foreman;
		if (foremanList != null && !foremanList.isEmpty()) {
			// 选择一个工头来处理
			return foremanList.get(Util.randomIndex(foremanList.size()));
		} else {
			return new ForemanSelectOneWorker(foremanType);
		}
		// return foreman;
	}

	private List<Foreman> getForemans(Work work, String foremanType)
			throws RemoteException {
		if (foremanType == null || "".equals(foremanType)) {
			return getForeman(work.getType());
		} else {
			return getForeman(foremanType);
		}
	}

	private List<Worker> cloneWorkers(List<Worker> acceptWorkers) {
		List<Worker> workers = new ArrayList<Worker>();
		for (Worker woker : acceptWorkers) {
			workers.add(woker);
		}
		return workers;
	}

	// 如果发生异常怎么办？
	public void autoMatch() throws IOException {
		Work work = workQueue.getIdleWork();
		if (work != null) {
			matchWork(work);
		}
	}

	public void stop() throws RemoteException {
		getRmiServer().stop();
	}

	private void matchWork(Work work) throws IOException {
		List<Foreman> foremans = getForeman(work.getType());
		List<Worker> workers = getWorkerList(work);
		// 这里是否要判断worker的长度
		// 如果没有worker是否直接抛出异常
		// 如果有worker才进行处理
		if (!foremans.isEmpty() && !workers.isEmpty()) {
			new Thread(new DoWorker(foremans.get(Util.randomIndex(foremans
					.size())), work, workers)).start();
		} else {
			new Thread(new DoWorker(new ForemanSelectOneWorker(work.getType()),
					work, workers)).start();
		}
	}

	class DoWorker implements Runnable {
		private final Work work;
		private final List<Worker> workers;
		private final Foreman foreman;

		public DoWorker(Foreman foreman, Work work, List<Worker> workers) {
			this.work = work;
			this.foreman = foreman;
			this.workers = workers;
		}

		public void run() {
			try {
				LOGGER.logMessage(LogLevel.DEBUG, "开始工作:{}-{}", work.getType(),
						work.getId());
				Warehouse outputWarehouse = foreman.work(work, workers);
				Work nextWork = work.getNextWork();
				if (nextWork != null) {
					nextWork.setInputWarehouse(outputWarehouse);
					// 添加新任务
					replaceWork(work, nextWork);
				} else {
					// 去掉老任务
					unregisterWork(work);
				}
				LOGGER.logMessage(LogLevel.DEBUG, "结束工作:{}-{}", work.getType(),
						work.getId());
			} catch (Exception e) {
				LOGGER.errorMessage("工作:{}-{}执行时发生异常！", e, work.getType(),
						work.getId());
			}
		}
	}
}
