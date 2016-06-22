package org.tinygroup.service.mbean;


public interface ServiceMonitorMBean {

	/**
	 * 
	/**
	 * 
	 * @description：获取本地service总数
	 * @author: qiucn
	 * @version: 2016年6月21日上午11:15:38
	 */
	public Integer getServiceTotal();
	
	/**
	 * 
	 * @description：是否存在该service
	 * @serviceId：服务id
	 * @author: qiucn
	 * @version: 2016年6月21日上午11:17:07
	 */
	public boolean isExistLocalService(String serviceId);
	
}

	