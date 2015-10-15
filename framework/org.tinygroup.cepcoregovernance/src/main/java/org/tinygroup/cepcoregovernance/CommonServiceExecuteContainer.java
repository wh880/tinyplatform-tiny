package org.tinygroup.cepcoregovernance;

import org.tinygroup.cepcoregovernance.container.CommonExecuteInfoContainer;
import org.tinygroup.cepcoregovernance.container.ExecuteTimeInfo;
import org.tinygroup.event.Event;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class CommonServiceExecuteContainer {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialServiceExecuteContainer.class);
	private static CommonExecuteInfoContainer localContainer = new CommonExecuteInfoContainer();
	private static CommonExecuteInfoContainer remoteContainer = new CommonExecuteInfoContainer();

	protected static void addLocalExecuteBefore(Event e) {
		localContainer.addExecuteBefore(e);
	}

	protected static void addLocalExecuteAfter(Event e) {
		localContainer.addExecuteAfter(e);
	}

	protected static void addRemoteExecuteBefore(Event e) {
		remoteContainer.addExecuteBefore(e);
	}

	protected static void addRemoteExecuteAfter(Event e) {
		remoteContainer.addExecuteAfter(e);
	}

	protected static void addExecuteException(Event e, Exception t) {
		if (localContainer.contain(e.getEventId())) {
			localContainer.addExecuteException(e,t);
		} else if (remoteContainer.contain(e.getEventId())) {
			remoteContainer.addExecuteException(e,t);
		} else {
			LOGGER.errorMessage("事件:" + e.getEventId() + ",服务:"
					+ e.getServiceRequest().getServiceId()
					+ ",在本地/远程列表中不存在");
		}
	}

	/**
	 * 返回本地服务调用次数
	 * @return
	 */
	public static Long getLocalTotalTimes(){
		return localContainer.getTotalTimes();
	}
	/**
	 * 返回本地服务调用成功次数(发生业务异常也算调用失败)
	 * @return
	 */
	public static Long getLocalSucessTimes(){
		return localContainer.getSucessTimes();
	}
	/**
	 * 返回本地服务调用发生异常次数(发生业务异常也算调用失败)
	 * @return
	 */
	public static Long getLocalExceptionTimes(){
		return localContainer.getExceptionTimes();
	}
	/**
	 * 返回远程
	 * @return
	 */
	public static Long getRemoteTotalTimes(){
		return remoteContainer.getTotalTimes();
	}
	/**
	 * 返回调用远程服务成功次数(发生业务异常也算调用失败)
	 * @return
	 */
	public static Long getRemoteSucessTimes(){
		return remoteContainer.getSucessTimes();
	}
	/**
	 * 返回调用远程服务发生异常次数(发生业务异常也算调用失败)
	 * @return
	 */
	public static Long getRemoteExceptionTimes(){
		return remoteContainer.getExceptionTimes();
	}
	/**
	 * 返回具体的远程服务调用时间统计数据
	 * @param serviceId 服务id
	 * @return
	 */
	public static ExecuteTimeInfo getRemoteServiceExecuteTimeInfo(String serviceId){
		return remoteContainer.getServiceExecuteTimeInfo(serviceId);
	}
	/**
	 * 返回具体的本地服务调用时间统计数据
	 * @param serviceId 服务id
	 * @return
	 */
	public static ExecuteTimeInfo getLocalServiceExecuteTimeInfo(String serviceId){
		return localContainer.getServiceExecuteTimeInfo(serviceId);
	}


}

