package org.tinygroup.flow.mbean;


public interface FlowMonitorMBean {

	/**
	 * 
	 * @description：获取本地逻辑流程服务总数
	 * @author: qiucn
	 * @version: 2016年6月21日上午11:15:59
	 */
	public Integer getFlowServiceTotal();
	
	/**
	 * 
	 * @description：是否存在逻辑该流程服务
	 * @flowId：流程服务ID
	 * @author: qiucn
	 * @version: 2016年6月21日上午11:17:29
	 */
	public boolean isExistFlowService(String flowId);
	
	/**
	 * 
	 * @description：获取本地页面流程服务总数
	 * @author: qiucn
	 * @version: 2016年6月21日上午11:15:59
	 */
	public Integer getPageFlowServiceTotal();
	
	/**
	 * 
	 * @description：是否存在页面该流程服务
	 * @flowId：流程服务ID
	 * @author: qiucn
	 * @version: 2016年6月21日上午11:17:29
	 */
	public boolean isExistPageFlowService(String pageflowId);
	
}

	