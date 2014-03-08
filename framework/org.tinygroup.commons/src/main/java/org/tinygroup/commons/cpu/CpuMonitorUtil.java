package org.tinygroup.commons.cpu;

/**
 * 
 * 功能说明:cpu监控信息工具类 
 * <p> 系统版本: v1.0<br>
 * 开发人员: renhui <br>
 * 开发时间: 2014-3-7 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */
public class CpuMonitorUtil {

	private static IMonitorService monitorService=new MonitorServiceImpl();
	
	public static MonitorInfoBean getMonitorInfoBean() throws Exception{
		return monitorService.getMonitorInfoBean();
	}
	
	public static double getCpuRatio() throws Exception{
		return new MonitorServiceImpl().getCpuRatioForWindows();		
	}
	
}
