/**;
 * 
 */
package org.tinygroup.service.release;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.service.release.config.ServiceRelease;

/**
 * 举几个场景
 * 1.只有白名单
 * 		那么在列的服务才是可用服务
 * 2.只有黑名单
 * 		那么除了黑名单中的服务以外，都是可用服务
 * 3.同时存在
 * 		优先黑名单，白名单作废
 * 4.没有配置黑白名单
 * 		那么服务没有被过滤
 * 
 * 本功能可多文件同时配置，请谨慎管理
 * 
 * @author yanwj
 *
 */
public class ServiceReleaseManager {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractFileProcessor.class);
	
	/**
	 * 黑名单
	 */
	private static Set<String> excludes = new HashSet<String>();
	
	/**
	 * 白名单
	 */
	private static Set<String> includes = new HashSet<String>();
	
	public static void add(ServiceRelease releaseInfo){
		if (releaseInfo.getExcludes() != null) {
			for (String item : releaseInfo.getExcludes().getItems()) {
				excludes.add(item);
			}
		}
		if (releaseInfo.getIncludes() != null) {
			for (String item : releaseInfo.getIncludes().getItems()) {
				includes.add(item);
			}
		}
	}
	
	public static void clear(){
		excludes.clear();
		includes.clear();
	}
	
	public static void reload(List<ServiceRelease> list){
		clear();
		for (ServiceRelease releaseInfo : list) {
			add(releaseInfo);
		}
	}
	
	/**
	 * true 为接受 ，false 为过滤
	 * 
	 * @param serviceId
	 * @return
	 */
	public static boolean isAccept(String serviceId){
		//没有名单，则一直为可用
		if (excludes.size() == 0 && includes.size() == 0) {
			return true;
		}
		//优先黑名单
		if (excludes.size() > 0) {
			return !excludes.contains(serviceId);
		}
		//最后白名单
		return includes.contains(serviceId);
	}
	
}
