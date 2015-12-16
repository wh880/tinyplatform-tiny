/**;
 * 
 */
package org.tinygroup.flow.release;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tinygroup.flow.release.config.PageFlowRelease;

/**
 * 举几个场景
 * 1.只有白名单
 * 		那么在列的才是可用
 * 2.只有黑名单
 * 		那么除了黑名单中的以外，都是可用
 * 3.同时存在
 * 		优先黑名单，白名单作废
 * 4.没有配置黑白名单
 * 		那么没有被过滤
 * 
 * 本功能可多文件同时配置，请谨慎管理
 * 
 * @author yanwj
 *
 */
public class PageFlowReleaseManager {

	/**
	 * 黑名单
	 */
	private static Set<String> excludes = new HashSet<String>();
	
	/**
	 * 白名单
	 */
	private static Set<String> includes = new HashSet<String>();
	
	public static void add(PageFlowRelease filter){
		if (filter.getExcludes() != null) {
			for (String item : filter.getExcludes().getItems()) {
				excludes.add(item);
			}
		}
		if (filter.getIncludes() != null) {
			for (String item : filter.getIncludes().getItems()) {
				includes.add(item);
			}
		}
	}
	
	public static void clear(){
		excludes.clear();
		includes.clear();
	}
	
	public static void reload(List<PageFlowRelease> list){
		clear();
		for (PageFlowRelease filter : list) {
			add(filter);
		}
	}
	
	/**
	 * true 为接受 ，false 为过滤
	 * 
	 * @param flowId
	 * @return
	 */
	public static boolean isAccept(String flowId){
		//没有名单，则一直为可用
		if (excludes.size() == 0 && includes.size() == 0) {
			return true;
		}
		//优先黑名单
		if (excludes.size() > 0) {
			return !excludes.contains(flowId);
		}
		//最后白名单
		return includes.contains(flowId);
	}
	
}
